# 예제

## **Context Switch Example**

### **1. 개요**
- 이 예제는 **Context Switching(문맥 교환)**이 어떻게 동작하는지 보여주는 코드이다. 
- 3개의 `Thread`가 **동시에 실행**되며, 실행 순서는 **CPU 스케줄러에 의해 동적으로 결정**된다.
- 각 `Thread`는 독립적으로 실행되지만, 실행 중인 스레드가 CPU 사용 시간을 초과하거나 `sleep()` 메서드로 인해 일시 정지될 경우, CPU는 다른 스레드로 전환된다.

### **2. 코드 분석**
```java
Thread thread1 = new Thread(() -> {
    for (int i = 0; i < 5; i++) {
        System.out.println("Thread 1: " + i);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
});
```
- `Thread thread1`이 생성되며, **람다식**을 사용하여 실행할 로직을 정의한다.
- `for` 루프를 통해 `0~4`까지 출력하며, `Thread.sleep(1)`을 호출하여 **1ms 동안 대기**한다.
- `sleep()`이 호출되면 현재 실행 중인 스레드는 일시 정지 상태가 되며, **CPU는 다른 스레드로 전환한다.(Context Switching 발생)**

```java
thread1.start();
thread2.start();
thread3.start();
```
- `thread1`, `thread2`, `thread3`가 **동시에 실행**되지만, 실행 순서는 **CPU 스케줄러에 의해 결정**된다.
- 각 스레드는 `sleep(1)`을 호출하기 때문에 CPU가 다른 스레드로 전환될 가능성이 높아진다.

### **3. Context Switching 발생 과정**
1. `thread1`이 실행됨 → `System.out.println("Thread 1: X")` 실행 → `sleep(1)` 호출
2. `thread1`이 **일시 정지 상태**가 되며, CPU가 다른 스레드로 전환됨 → `thread2` 실행
3. `thread2`가 실행됨 → `System.out.println("Thread 2: X")` 실행 → `sleep(1)` 호출
4. `thread2`가 **일시 정지 상태**가 되며, `thread3` 실행 → 반복
5. 이후 **스레드 간 컨텍스트 스위칭이 계속 발생**하며 모든 스레드가 종료될 때까지 진행됨.

### **4. CPU 스케줄링과 Context Switching**
- CPU는 각 스레드가 `sleep()`을 호출하거나 타임 슬라이스(Time Slice)가 초과될 때마다 **다른 스레드로 전환(Context Switching)**한다.
- 실행 순서는 **OS의 스케줄러가 결정**하며, **우선순위(priority)와 스케줄링 정책(FIFO, Round Robin 등)에 따라 다르게 실행**될 수 있다.
- `sleep(1)`을 호출하지 않는다면, 특정 스레드가 **연속적으로 실행되는 현상(Starvation, 기아 현상)**이 발생할 수도 있다.

### **5. 결론**
- **Context Switching**은 멀티스레드 환경에서 필수적으로 발생하는 과정이며, 스레드가 `sleep()`을 호출하거나 CPU의 타임 슬라이스가 초과될 때 발생한다.
- 이 예제는 **멀티스레딩의 기본 동작 방식**을 이해하는 데 유용하며, 실행 결과는 항상 다를 수 있다.
- 스레드 간의 **작업 순서를 제어하려면 `join()`이나 `synchronized` 블록을 활용**하는 방법이 있다.

> ⚠ **주의:** Context Switching은 **CPU 성능을 소모하는 작업**이므로, 과도한 스레드 생성은 성능 저하로 이어질 수 있음에 주의한다.

