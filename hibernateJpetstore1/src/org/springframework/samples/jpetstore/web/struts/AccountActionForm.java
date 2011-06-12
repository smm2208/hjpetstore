package org.springframework.samples.jpetstore.web.struts;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.samples.jpetstore.domain.Account;

public class AccountActionForm extends BaseActionForm {
    
    /** 用于检验的常量定义，因为在新建帐户与修改帐户时检验逻辑是不一样的。
     * 至少在修改帐户时，帐户名是已经存在了 */
    public static final String VALIDATE_EDIT_ACCOUNT = "editAccount";
    public static final String VALIDATE_NEW_ACCOUNT = "newAccount";
    
    /** 用于存贮用户的首先语言的列表 */
    private static final ArrayList LANGUAGE_LIST = new ArrayList();
    
    /* Private Fields */
    // 看起来好象与 Account 中的成员重复了，这是因为此 Form 被多个页面重复使用的
    // 结果，因为在登录页面时，那时根本不存在 Account, 所以不可能通过
    // account.getUsername() 和 account.getPassword() 来得到用户的输入值的，
    
    // 下面两项即是在登录当时收集输入 信息，
    // 其它情况（比如修改，新建帐户时）都是间接使用了 Account 中的成员，因当时都
    // 已经在 session 中存放了一个 Account 的实例
    
    // 所以重用是有代价的（使代码不那么直观了，如果是一个页面表单 Form 对应一个
    // FormBean 的话，以下成员与页面中的输入元素是一一对应的）
    
    // 供登录页面使用的 元素
    private String username;
    private String password;
    
    // 登录后，与帐户相关的元素
    private String repeatedPassword;
    private List languages;
    private List categories;
    
    /**
     * 这个成员的值是通过页面隐藏元素传入的：
     * NewAccountForm.jsp 中: <html:hidden name="workingAccountForm" property="validate" value="newAccount"/>
     * EditAccountForm.jsp 中:<html:hidden name="workingAccountForm" property="validate" value="editAccount" />
     */
    private String validate;
    
    /**
     * 用来记住用户是从哪里跳转过来的，因为准备对购物车进行结算时，如果没有登录
     * 的话，首先将结算中心页面的地址存入此成员中，登录成功后再跳转过去。
     * 如果没有这样一步操作的话，那么就会出现讨厌的将你送回首页面(也就是程序
     * 的逻辑流程打扰了用户的进程，这是最应当避免的。)
     */
    private String forwardAction;
    
    /**
     * 所有的帐号信息放在这个 POJO 中
     */
    private Account account;
    
    // added by pprun
    /**
     * 用于显示标语，当你在用户信息页面选择显示标语时
     */
    private String bannerName;
    
    /**
     * 用于显示根据用户的喜好被推荐的宠物列表,当你选择了显示该列表时。
     */
    private PagedListHolder myList;
    
    /**
     * 用户最喜欢的宠物类别
     */
    private String favCategoryName;
    
    /* Static Initializer */
    
    static {
        LANGUAGE_LIST.add("english");
        LANGUAGE_LIST.add("japanese");
    }
    
    public AccountActionForm() {
        languages = LANGUAGE_LIST;
    }
    
    public PagedListHolder getMyList() {
        return myList;
    }
    public void setMyList(PagedListHolder myList) {
        this.myList = myList;
    }
    
    public String getForwardAction() {
        return forwardAction;
    }
    public void setForwardAction(String forwardAction) {
        this.forwardAction = forwardAction;
    }
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRepeatedPassword() {
        return repeatedPassword;
    }
    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }
    
    public Account getAccount() {
        return account;
    }
    
    public void setAccount(Account account) {
        this.account = account;
    }
    
    public List getLanguages() {
        return languages;
    }
    public void setLanguages(List languages) {
        this.languages = languages;
    }
    
    public List getCategories() {
        return categories;
    }
    public void setCategories(List categories) {
        this.categories = categories;
    }
    
    public String getBannerName() {
        return bannerName;
    }
    
    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }
    
    public String getFavCategoryName() {
        return favCategoryName;
    }
    
    public void setFavCategoryName(String favCategoryName) {
        this.favCategoryName = favCategoryName;
    }
    
    public String getValidate() {
        return validate;
    }
    public void setValidate(String validate) {
        this.validate = validate;
    }
    
    /**
     * 覆盖父类中的方法，用于输入校验
     */
    public void doValidate(ActionMapping mapping,
            HttpServletRequest request, List errors) {
        if (validate != null) {
            if (VALIDATE_EDIT_ACCOUNT.equals(validate) ||
                    VALIDATE_NEW_ACCOUNT.equals(validate)) {
                if (VALIDATE_NEW_ACCOUNT.equals(validate)) {
                    // 是新建帐户时，需要额外的校验
                    
                    account.setStatus("OK");
                    addErrorIfStringEmpty(errors, "User ID is required.",
                            account.getUsername());
                    
                    if (account.getPassword() == null ||
                            account.getPassword().length() < 1 ||
                            !account.getPassword().equals(repeatedPassword)) {
                        errors.add("Passwords did not match or were not provided.  " +
                                "Matching passwords are required.");
                    }
                }
                
                if (account.getPassword() != null &&
                        account.getPassword().length() > 0) {
                    if (!account.getPassword().equals(repeatedPassword)) {
                        errors.add("Passwords did not match.");
                    }
                }
                
                addErrorIfStringEmpty(errors, "First name is required.",
                        this.account.getFirstname());
                addErrorIfStringEmpty(errors, "Last name is required.",
                        this.account.getLastname());
                addErrorIfStringEmpty(errors, "Email address is required.",
                        this.account.getEmail());
                addErrorIfStringEmpty(errors, "Phone number is required.",
                        this.account.getPhone());
                addErrorIfStringEmpty(errors, "Address (1) is required.",
                        this.account.getUserAddr().getAddr1());
                addErrorIfStringEmpty(errors, "City is required.",
                        this.account.getUserAddr().getCity());
                addErrorIfStringEmpty(errors, "State is required.",
                        this.account.getUserAddr().getState());
                addErrorIfStringEmpty(errors, "ZIP is required.",
                        this.account.getUserAddr().getZipcode());
                addErrorIfStringEmpty(errors, "Country is required.",
                        this.account.getUserAddr().getCountry());
            }
        }
    }
    
    /**
     * 此方法是一个很重要的方法，我们看看基类中对该方法的描述:
     *
     * Reset bean properties to their default state, as needed.
     * This method is called before the properties are repopulated by the controller.
     * 在需要时，复位 Bean 的属性值，此方法是在控制器重新组装Bean的属性值之前调用的。
     *
     * The default implementation does nothing. In practice, the only properties
     * that need to be reset are those which represent checkboxes on a
     * session-scoped form. Otherwise, properties can be given initial values
     * where the field is declared.
     * 默认的实现，并没有做任何事。实际上，唯一需要重置的属性是那些基于
     * session 作用域的复选框页面元素。否则这些元素将使用页面上声明的默认值。
     * 是勾选还是未勾选。
     *
     * If the form is stored in session-scope so that values can be collected
     * over multiple requests (a "wizard"), you must be very careful of which properties,
     * if any, are reset. As mentioned, session-scope checkboxes must be reset to
     * false for any page where this property is set. This is because the client
     * does not submit a checkbox value when it is clear (false).
     * If a session-scoped checkbox is not proactively reset, it can never be set to false.
     * 假如表单是存贮在 Session 作用域中(如：
     * <action path="/shop/signon" type="org.springframework.samples.jpetstore.web.struts.SignonAction"
     * name="accountForm" scope="session" validate="false">
     * 即声明为 session 范围的 formBean)的话，表单元素的值可以在多个请求(即多页向导性页面)
     * 中被收集，此时必须小心对等哪些输入域必须重置。象我们前面所述，session
     * 作用域范围内的 checkbox(复选按钮)，在为它们设置值之前必须重置为 false，
     * 因为客户端(即浏览器)在复选按钮未被勾选时并不会发送任何值到服务器端。(否则，
     * 就出现这样的问题：如果之前该复选按钮是勾选状态，并且用户请求这一页面
     * 该按钮显示为勾选状态，在后续的操作中，用户取消选中状态。但是因为 checkbox
     * 在取消选中状态后，浏览器并不发送任何关于这个控件的信息，但 ActionForm 中
     * 要改变控制的状态，必须比较浏览器传上来的状态和当前状态，但因为浏览器并未
     * 告知它，所以 ActionForm 认为这个控件的状态并未改变。因为从这时开始，无论
     * 用户怎么做，这个控件将永远保持为选中状态)
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        super.reset(mapping, request);
        setUsername(null);
        setPassword(null);
        setRepeatedPassword(null);
        
        // BUG here: by pprun
        // 按照此方法的 api 文档说明，说 checkbox 的值必须在此复位，
        //可是 NewAccountForm.jsp 中 Enable MyList 和 Enable MyBanner 却没有
        // 所以当用户第一次选中后，以后想改为未选中是没门了，(除了象程序控制那样：
        // 比如：      acctForm.getAccount().setDisplayMylist(
        //          request.getParameter("account.displayMylist") != null);
        //   acctForm.getAccount().setDisplayBanner(
        //          request.getParameter("account.displayBanner") != null);)
        //
        // 但是当输入错误时重新显示当前页面时，上次选为未选中状态被丢失了！
        //
        // 因为按照 api 的说明
        // 当 checkbox 为未选中状态时，浏览器是不会发信息到服务器端的，所以
        // struts 无法设置其值
        // 解决办法：
        if (getAccount() != null) {
            getAccount().setDisplayMylist(false);
            getAccount().setDisplayBanner(false);
        }
    }
}
