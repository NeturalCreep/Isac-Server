public class clearScreen{
//ʵ�����������Ľӿ�
public native static void clearScreen();

//����dll�ļ�
{
System.loadLibrary("Clearx64");//Clear ����dll���ļ���
}

}