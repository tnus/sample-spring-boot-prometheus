# Spring Boot Prometheus Example

Sample project to demonstrate the spring boot prometheus / grafana integration

# Installation

- Prometheus must be installed
- Grafana must be installed
- The Spring Boot application can be build with mvn install

# Application Urls

- [Prometheus](http://localhost:9090)
- [Grafana](http://localhost:3000)
  
  Username and password is default admin / amdin  
    	
- [Spring Boot Sample](http://localhost:8080)

# Prometheus Configuration

The following content must be placed in the prometheus.yml file

```
  - job_name: 'sample-spring-boot-prometheus'
    metrics_path: /actuator/prometheus
    static_configs:
      - targets: ['localhost:8080', 'localhost:8081', 'localhost:8082']
        labels: 
          application: 'sample-spring-boot-prometheus'
```

# Grafana

Import the prometheus datasource and the following spring boot [statistic dashboard](https://grafana.com/dashboards/6756)      