## File

---

둘 간의 차이점
- file.getAbsolutePath()
- file.getCanonicalPath()

C:\temp\a 라는 경로에서 C:\temp\b 의 경로로 이동하려고 할 때
"..\b" 와 같이 상대 경로로 움직일 수 있음.

이 경우, <br>
- AbsolutePath 리턴값은 C:\temp\a..\b
- CanonicalPath 리턴값은 C:\temp\b

가 된다.

풀어서 설명하면 CanonicalPath 는 절대적으로 유일하게 표현할 수 있는 경로를 뜻한다.


---

### 디렉터리에 있는 목록을 살펴보기 위한 list methods

```java
String[] list(FilenameFilter filter)
File[] listFiles(FileFilter filter)
File[] listFiles(FilenameFilter filter)
....
```

> 매개변수인 FilenameFilter 와 FileFilter 로 디렉토리에서 필요한 확장자만 가져올 수 있음


FileFilter Interface에 선언되어 있는 메소드 : 

```java
boolean accept(File pathname) // 매개 변수로 넘어온 File 객체가 조건에 맞는지 확인
boolean accept(File dir, String name) // 매개 변수로 넘어온 dir 에 있는 파일 이름이 조건에 맞는지 확인
```

interface 이므로 다음과같이 구현해서 사용하면 된다.

```java
public class JPGFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        if (pathname.isFile()) {
            String fileName = pathname.getName();
            if (fileName.endsWith(".jpg")) {
                return true;
            }
        }
        return false;
    }
}
```


```java
//main method
File[] mainFileList = file.listFiles(new JPGFilter());
```

---

**[참고] java 7 이상일 경우 File 클래스보다 java.nio.file 패키지의 Files 클래스를 사용하는 것이 더
효과적이다.**





