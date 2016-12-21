package com.uisftech.cloan.limit.config;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.apache.commons.configuration.DatabaseConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.instance.HazelcastInstanceFactory;
import com.uisftech.cloan.limit.config.CacheConfiguration;
import com.uisftech.cloan.limit.config.MetricsConfiguration;
import com.uisftech.cloan.limit.config.CloanLimitProperties;

@SuppressWarnings("unused")
@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class, DatabaseConfiguration.class })
public class CacheConfiguration {

	private static final Logger log = LoggerFactory.getLogger(CacheConfiguration.class);

	private static HazelcastInstance hazelcastInstance;

	@Inject
	private Environment env;

	@Inject
	private DiscoveryClient discoveryClient;

	@Inject
	private ServerProperties serverProperties;

	private CacheManager cacheManager;

	@PreDestroy
	public void destroy() {
		log.info("Closing Cache Manager");
		Hazelcast.shutdownAll();
	}

	@Bean
	public CacheManager cacheManager(HazelcastInstance hazelcastInstance) {
		log.debug("Starting HazelcastCacheManager");
		cacheManager = new com.hazelcast.spring.cache.HazelcastCacheManager(hazelcastInstance);
		return cacheManager;
	}

	@Bean
	public HazelcastInstance hazelcastInstance(CloanLimitProperties scfsProductProperties) {
		log.debug("Configuring Hazelcast");
		Config config = new Config();
		config.setInstanceName("scfsOrgMs");
		String serviceId = discoveryClient.getLocalServiceInstance().getServiceId();
		log.debug("Configuring Hazelcast clustering for instanceId: {}", serviceId);

		if (env.acceptsProfiles(com.uisftech.cloan.limit.constants.Constants.SPRING_PROFILE_DEVELOPMENT)) {
			log.debug("Application is running with the \"dev\" profile, Hazelcast "
					+ "cluster will only work with localhost instances");

			System.setProperty("hazelcast.local.localAddress", "127.0.0.1");
			config.getNetworkConfig().setPort(serverProperties.getPort() + 5701);
			config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
			config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
			for (ServiceInstance instance : discoveryClient.getInstances(serviceId)) {
				String clusterMember = "127.0.0.1:" + (instance.getPort() + 5701);
				log.debug("Adding Hazelcast (dev) cluster member " + clusterMember);
				config.getNetworkConfig().getJoin().getTcpIpConfig().addMember(clusterMember);
			}
		} else { // Production configuration, one host per instance all using
					// port 5701
			config.getNetworkConfig().setPort(5701);
			config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
			config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
			for (ServiceInstance instance : discoveryClient.getInstances(serviceId)) {
				String clusterMember = instance.getHost() + ":5701";
				log.debug("Adding Hazelcast (prod) cluster member " + clusterMember);
				config.getNetworkConfig().getJoin().getTcpIpConfig().addMember(clusterMember);
			}
		}

		config.getMapConfigs().put("default", initializeDefaultMapConfig());
		config.getMapConfigs().put("com.hoperun.scfs.org.domain.*", initializeDomainMapConfig(scfsProductProperties));

		hazelcastInstance = HazelcastInstanceFactory.newHazelcastInstance(config);

		return hazelcastInstance;
	}

	private MapConfig initializeDefaultMapConfig() {
		MapConfig mapConfig = new MapConfig();

		mapConfig.setBackupCount(0);

		mapConfig.setEvictionPolicy(EvictionPolicy.LRU);

		mapConfig.setMaxSizeConfig(new MaxSizeConfig(0, MaxSizeConfig.MaxSizePolicy.USED_HEAP_SIZE));

		mapConfig.setEvictionPercentage(25);

		return mapConfig;
	}

	private MapConfig initializeDomainMapConfig(CloanLimitProperties scfsProductProperties) {
		MapConfig mapConfig = new MapConfig();

		mapConfig.setTimeToLiveSeconds(scfsProductProperties.getCache().getTimeToLiveSeconds());
		return mapConfig;
	}

	/**
	 * @return the unique instance.
	 */
	public static HazelcastInstance getHazelcastInstance() {
		return hazelcastInstance;
	}
}
