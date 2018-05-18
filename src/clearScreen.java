public class clearScreen{
//实现清屏方法的接口
public native static void clearScreen();

//加载dll文件
{
System.loadLibrary("Clearx64");//Clear 生成dll的文件名
}

}