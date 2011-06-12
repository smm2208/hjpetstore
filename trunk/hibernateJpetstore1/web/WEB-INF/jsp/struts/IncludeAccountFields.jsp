<FONT color=darkgreen><H3>Account Information</H3></FONT>

<TABLE bgcolor="#008800" border=0 cellpadding=3 cellspacing=1 bgcolor="#FFFF88">
    <TR bgcolor="#FFFF88"><TD>
        First name:</TD><TD><html:text name="workingAccountForm" property="account.firstname" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
        Last name:</TD><TD><html:text name="workingAccountForm" property="account.lastname" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
        Email:</TD><TD><html:text size="40" name="workingAccountForm" property="account.email" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
        Phone:</TD><TD><html:text name="workingAccountForm" property="account.phone" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
        Address 1:</TD><TD><html:text size="40" name="workingAccountForm" property="account.userAddr.addr1" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
        Address 2:</TD><TD><html:text size="40" name="workingAccountForm" property="account.userAddr.addr2" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
        City: </TD><TD><html:text name="workingAccountForm" property="account.userAddr.city" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
        State:</TD><TD><html:text size="4" name="workingAccountForm" property="account.userAddr.state" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
        Zip:</TD><TD><html:text size="10" name="workingAccountForm" property="account.userAddr.zipcode" />
    </TD></TR>
    <TR bgcolor="#FFFF88"><TD>
        Country: </TD><TD><html:text size="15" name="workingAccountForm" property="account.userAddr.country" />
    </TD></TR>
</TABLE>

<FONT color=darkgreen><H3>Profile Information</H3></FONT>

<TABLE bgcolor="#008800" border=0 cellpadding=3 cellspacing=1 >
    <TR bgcolor="#FFFF88"><TD>
        Language Preference:</TD><TD>
            <html:select name="workingAccountForm" property="account.langPreference">
                <html:options name="workingAccountForm" property="languages" />
            </html:select>
    </TD></TR><TR bgcolor="#FFFF88"><TD>
        Favourite Category:</TD><TD>
            <html:select name="workingAccountForm" property="favCategoryName">
                <html:options name="workingAccountForm" property="categories" />
            </html:select>
    </TD></TR><TR bgcolor="#FFFF88"><TD colspan=2>
            <html:checkbox name="workingAccountForm" property="account.displayMylist"/> Display My Favorite List
    </TD></TR><TR bgcolor="#FFFF88"><TD colspan=2>
            <html:checkbox name="workingAccountForm" property="account.displayBanner"/> Display the Banner
    </TD></TR>
</TABLE>
