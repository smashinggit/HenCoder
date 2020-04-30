
# 字节、字符
- bit 比特，  二进制数据0或1
- byte 字节, 是计算机中数据处理的基本单位， 存储空间的的基本计量单位
  一个字节包含8个位，所以，byte 类型的取值范围是-128到127

  1 byte = 8 bit 
  1 个英文字母 = 1 byte = 8 bit 
  1 个汉字 = 2 byte = 16 bit
  
   
- char 字符，是指计算机中使用的字母、数字、字和符号。依据字符不同的编码格式，每个字符单位对应的字节数是不一样的

按照ANSI编码标准，标点符号、数字、大小写字母都占一个字节，汉字占2个字节。
按照UNICODE标准所有字符都占2个字节

# 流

## InputStream、OutputStream它们是处理字节流的
在文件的输入输出中，InputStream、OutputStream它们是处理字节流的，就是说假设所有东西都是二进制的字节

## Reader、Writer
Reader, Writer 则是字符流，它涉及到字符集的问题；
按照ANSI编码标准，标点符号、数字、大小写字母都占一个字节，汉字占2个字节
按照UNICODE标准所有字符都占2个字节

## BufferedWriter、BufferedReader

目的是减少I/O操作，提高性能

# 路径
-  /开头：代表根目录

```
  val file = File("/read.txt")
```
会在项目所在磁盘的根目录创建，比如项目工程在D盘中，那文件就创建在 D://read.txt


- ./  代表目前所在的目录

```
       val file = File("./read.txt")
```
目前所在的目录指的是当前项目工程的目录，并不是写代码的类所在的目录
上面的代码会在 D:/HenCoderStudy/read.txt

        
- ../  代表上一层目录


