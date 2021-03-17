package bat.ke.qq.com.dao;

import bat.ke.qq.com.anno.Dao;
import bat.ke.qq.com.bean.Fox;


public interface IFoxDao {
	public void query();
	public void query(String name);
	public void query(String name, String sex);
	public void query(Fox fox);
}
