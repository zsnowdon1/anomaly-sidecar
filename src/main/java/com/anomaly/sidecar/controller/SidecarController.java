package com.anomaly.sidecar.controller;

import com.anomaly.sidecar.entity.MetricData;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SidecarController {
    private final MeterRegistry meterRegistry;

    public SidecarController(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @PostMapping("/metrics")
    public void collectMetrics(@RequestBody MetricData data) {
        meterRegistry.counter("app.errors", "service", data.getServiceName())
                .increment(data.getErrorCount());
        meterRegistry.gauge("app.latency", data.getLatency());
        if (data.getLatency() > 500) {
            meterRegistry.counter("anomaly.detected", "type", "high_latency").increment();
        }
    }
}