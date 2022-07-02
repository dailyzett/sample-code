### Fork/Join

> Java 7 에서 Fork/Join 은 Work stealing 개념 포함

- 여러개의 Dequeue 에 작업이 나누어져 있을 때, 한 쪽은 바쁘고 한 쪽은 한가할 때 한가한 쪽이 바쁜 쪽 Queue의 일을 가져가는 것을 Work stealing 이라고 한다.
- Fork/Join에 기본적으로 포함되어 있으므로 구현할 필요 없음.
- java.util.concurrent 패키지의 RecursiveAction과 RecursiveTask라는 추상 클래스를 이용

```java
public abstract class RecursiveAction extends ForkJoinTask<Void>
public abstract class RecursiveTask<V> extends ForkJoinTask<V>
```

ForkJoinTask 추상 클래스는 다음과 같다.

```java
public abstract ForkJoinTask<V> extends Object implements Future<V>, Serializable
```

#### Future Interface

- Java 5 부터 추가된 인터페이스
- 비동기적인 요청을 하고 응답을 기다릴 때 사용

|                   | Fork/Join 클라이언트 밖     | Fork/Join 클라이언트 내부    |
|:------------------|:----------------------|:----------------------|
| 비 동기적 호출 수행 시     | execute(ForkJoinTask) | ForkJoinTask.fork()   |
| 호출 후 결과 대기        | invoke(ForkJoinTask)  | ForkJoinTask.invoke() |
| 호출 후 Future 객체 수신 | submit(ForkJoinTask)  | ForkJoinTask.fork()   |



----

### NIO2

Java 6 까지 사용된 File 클래스의 단점
- 심볼릭 링크, 속성, 파일의 권한 등에 대한 기능 X
- 파일을 삭제하는 delete() 메소드는 실패시 아무런 예외 발생 X, boolean 타입의 결과만 제공
- 파일 변경 확인은 lastModified() 메소드에서 제공해주는 long 타입의 결과로 이전 시간과 비교하는 방법 밖에 없었으며, 이 메소드가 호출되면 연계되어 호출되는 클래스가 다수 존재하며 성능상 문제도 많음


#### NIO2 File Class

- Paths : Path 인터페이스 객체를 얻을 수 있다.
- Files : 기존 File 클래스에서 제공되던 클래스의 단점들을 보완
- FileSystems : 현재 사용중인 파일 시스템에 대한 정보를 처리하는 데 필요한 메소드를 제공
- FileStore : 파일을 저장하는 디바이스, 파티션, 볼륨 등에 대한 정보들을 확인하는 데 필요한 메소드를 제공


#### NIO2 Write And Read Example Code

```java
public Path writeFile(Path path) throws Exception{
    Charset charset = Charset.forName("EUC-KR");
    List<String> contents = getContents();
    StandardOpenOption openOP = StandardOpenOption.CREATE;
    return Files.write(path, contents, charset, openOP);
}

public void readFile(Path path) throws Exception {
    Charset charset = Charset.forName("EUC-KR");
    System.out.println("Path=" + path);
    List<String> fileContents = Files.readAllLines(path, charset);
    for (String x : fileContents) {
        System.out.println(x);
    }
    System.out.println();
}
```

#### Files 클래스 Copy / Move / Delete 사용 예제

```java
Path toPath = fromPath.toAbsolutePath().getParent();

//Make a directory if it is not exist.
Path copyPath = toPath.resolve("copied");
if (!Files.notExists(copyPath)) {
    Files.createDirectories(copyPath);
}

//Copy file
Path copiedFilePath = copyPath.resolve(fileName);
StandardCopyOption copyOption = StandardCopyOption.REPLACE_EXISTING;
Files.copy(fromPath, copiedFilePath, copyOption);

// Read Copies file
System.out.println("***** Copied file contents *****");
readFile(copiedFilePath);

// Move file
Path movedFilePath = Files.move(copiedFilePath, copyPath.resolve("moved.txt"), copyOption);

// Delete Files
Files.delete(movedFilePath);
Files.delete(copyPath);
```

#### 파일과 관련된 기타 API

- SeekableByteChannel
  - 바이트 기반의 채널을 처리하는데 사용.
  - 현재의 위치를 관리, 해당 위치가 변경된든 것을 허용
- NetworkChannel 및 MulticastChannel
  - 네트워크 소켓을 처리하기 위한 채널
  - MulticastChannel 은 IP 멀티캐스트를 지원하는 채널
- AsynchronousFileChannel
  - 비동기 파일 채널 처리
  - AsynchronousFileChannelGroup 제공, 비동기적인 처리를 하는 쓰레드 풀을 제공