package zxx.sync.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionModifyExceptionTest {
	public static void main(String[] args) {
//		collection users = new ArrayList();
		Collection users = new CopyOnWriteArrayList<User>();
		users.add(new User("张三",28));	
		users.add(new User("李四",25));			
		users.add(new User("王五",31));	
		Iterator itrUsers = users.iterator();
		while(itrUsers.hasNext()){
			User user = (User)itrUsers.next();
			if("王五".equals(user.getName())){
				users.remove(user);
				//itrUsers.remove();
			} else {
				System.out.println(user);				
			}
		}
	}
}