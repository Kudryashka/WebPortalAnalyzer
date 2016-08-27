package name.dimasik.dev.web.portalanalyzer.userinfo;

import java.util.Date;
import java.util.List;

/**
 * TODO add description
 *
 * @author Dmytro Kudria
 * @author <a href="http://dimasik.name">http://dimasik.name</a>
 *
 */
public interface UserInfoDAO {

	/**
	 * TODO
	 * @param info
	 * @param date
	 */
	public void registerUserInfo(UserRequestInfo info, Date date);
	
	/**
	 * TODO
	 * @param days
	 * @return
	 */
	public List<UserRequestInfo> getUsersInfos(int days);
}
