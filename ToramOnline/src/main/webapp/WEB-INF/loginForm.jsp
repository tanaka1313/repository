<c:import url="layout.jsp">
  <c:param name="title" value="ログイン"/>
  <c:param name="content">
         <h2>ログインフォーム</h2>
         <c:if test="${param.containsKey('error')}">
            <span style="color: red;">
                <c:out value="ユーザー名/パスワードのどちらかが間違っています。" />
            </span>
         </c:if>
         <c:url var="loginUrl" value="/login" />
         <form:form action="${loginUrl}">
            <table>
                <tr>
                    <td><label for="username">ユーザー名</label></td>
                    <td><input type="text" id="username" name="username" /></td>
                </tr>
                <tr>
                    <td><label for="password">パスワード</label></td>
                    <td><input type="text" id="password" name="password" /></td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td><button>ログイン</button></td>
                </tr>
            </table>
         </form:form>
         <h2>このページについて</h2>
         <p>
            このページは試験運用で作成したページです。<br />
            試験運用のページについては認証を必要としております。
         </p>
         <p>
            そのうち試験運用だけでなく、本運用でも認証機能を追加したいですが難航しております。
         </p>
  </c:param>
</c:import>
