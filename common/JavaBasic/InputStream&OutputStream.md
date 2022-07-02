## InputStream 과 OutputStream

---

InputStream, OutputStream 은 byte를 다루기 위한 클래스<br>
Reader, Writer 은 char 기반의 문자열을 다루기 위한 클래스


Writer 클래스로 텍스르를 쓸 때 예시: 
- null 초기화
- 값 대입
- 작업 후 finally 내부에서 close (close 하는 순서는 초기화한 역순으로)
```java
private void writeFile(String fullPath, int numberCount) {
    FileWriter fileWriter = null;
    BufferedWriter bufferedWriter = null;

    try {
        fileWriter = new FileWriter(fullPath);
        bufferedWriter = new BufferedWriter(fileWriter);
        for(int i = 0; i <= numberCount; i++){
            bufferedWriter.write(Integer.toString(i));
            bufferedWriter.newLine();
        }
        System.out.println("Write success !!!");
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (bufferedWriter != null) {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (fileWriter != null){
            try{
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
```

Reader 클래스는 절차는 모두 같지만 한 줄이 다르다

```java
String data;
while((data=bufferedReader.readLine()) != null){
    System.out.println(data);
}
```

