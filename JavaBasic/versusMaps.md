##HashMap vs HashTable vs ConcurrentHashMap

HashMap 은 싱글 쓰레드에서 사용하고
HashTable과 ConcurrentHashMap 은 멀티 쓰레드 환경에서 사용한다. 따라서 이들간의 차이점을 표로 정리하면 다음과 같다.

| HashMap                      | HashTable                                                                           | ConcurrentHashMap                                                                       |
|:-----------------------------|:------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------|
| 쓰레드에 안전하지 못함                 | 전채 맵 객체를 잠그면 thread-safe                                                            | 버킷 수준 잠금으로 전체 맵 객체를 잠그지 않고도 thread-safe 달성                                              |
| 해당없음                         | 한 번에 하나의 스레드가 맵 객체에서 작동                                                             | 한 번에 여러 스레드가 맵 객체에서 안전하게 작동                                                             |
| 해당없음                         | 모든 읽기 및 쓰기 작업에 전체 맵 객체가 요구                                                          | 읽기 작업은 잠금 없이 수행하지만 쓰기 작업은 버킷 레벨의 잠금을 수행                                                 |
| 해당없음                         | 한 쓰레드가 맵 객체를 순회하는 동안 다른 쓰레드들은 맵 객체를 변경하지 못함. ConcurrentModificationException 예외를 던짐 | 한 쓰레드가 맵 객체를 순회하는 동안에도 다른 쓰레드는 맵 객체를 수정할 수 있으며 ConcurrentModificationException 을 던지지 않음 |
| HashMap의 iterator은 fail-fast | HashTable의 iterator은 fail-fast이며 ConcurrentModificationException 예외 발생              | ConcurrentHashMap의 Iterator은 fail-safe 이고 예외를 던지지 않는다.                                  |
| 키와 값에 NULL을 허용한다.            | 키와 값에 NULL을 허용하지 않는다.                                                               | 키와 값에 NULL을 허용하지 않는다.                                                                   |
| 1.2 버전                       | 1.0버전                                                                               | 1.5버전                                                                                   |

