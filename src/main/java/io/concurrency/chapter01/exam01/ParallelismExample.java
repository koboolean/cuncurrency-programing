package io.concurrency.chapter01.exam01;

import java.util.ArrayList;
import java.util.List;

/**
 * parallelStream()을 사용할 때는 적절한 데이터 크기와 CPU 개수를 고려하여 사용해야 최적의 성능을 낼 수 있다.
 */
public class ParallelismExample {
    public static void main(String[] args) {

        // CPU 개수만큼 작업을 수행한다.
        int cpuCores = Runtime.getRuntime().availableProcessors();

        // CPU 개수만큼 데이터를 생성
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < cpuCores; i++) {
            data.add(i);
        }

        // CPU 개수만큼 데이터를 병렬로 처리
        long startTime = System.currentTimeMillis();

        // CPU Core 만큼의 데이터를 각 스레드에서 병렬처리하기 위해 사용한다.
        // parallelStream()은 Collection 객체에 존재하는 함수로 ForkJoinPull 형식으로 작업이 이루어진다.
        // 병렬성을 사용하지 않고 작업을 수행 시 stream() 형식으로 작업을 실행한다.
        long sum = data.parallelStream()
                .mapToLong(i -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return i * i;
                })
                .sum();

        long endTime = System.currentTimeMillis();

        System.out.println("CPU 개수만큼 데이터를 병렬로 처리하는 데 걸린 시간: " + (endTime - startTime) + "ms");
        System.out.println("결과 : " + sum);

    }
}
