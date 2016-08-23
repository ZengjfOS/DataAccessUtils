# DataAccessUtils

在Android下除了使用SP保存一些属性，也可以通过操作文件的方式来保存一些内容，这个工具操作文件用于保存、获取任意长的整形数组

## 使用方法

```Java
    static public void testDemo(Activity activity) {
        int[] data = {1, 2, 3, 4, 5, 6};
        try {
            // 将数组写入默认的文件
            writeIntArray(activity, data);

            // 从默认的文件中读取6个整数，并存放在数组中
            int[] redata = readIntArray(activity, 6);

            Log.e("aplexos utils", Arrays.toString(redata));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
```