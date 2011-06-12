<br>
<c:if test="${!empty accountForm.account.username}">
  <c:if test="${accountForm.account.displayBanner}">
    <table align="center" background="../images/bkg-topbar.gif" cellpadding="5" width="100%">
      <tr><td>
          <center>
            <c:out value="${accountForm.bannerName}" escapeXml="false"/>
            &nbsp;
          </center>
      </td></tr>
    </table>
  </c:if>
</c:if>
