<c:import url="seirenAll.jsp">
  <c:param name="titleData" value="精錬シミュレータ" />
  <c:param name="contentFirst">
<h2>精錬に必要なスピナと鉱石の期待値</h2>
<p>
ここでは精錬に必要な鉱石の個数やスピナの期待値を算出します。
</p>
    </c:param>
    <c:param name="contentEnd">
<h2>実行結果</h2>
<a name="output"></a>
<p>
精錬値<c:out value="${outputForm.syokiSeirenHyoji}" />から<c:out value="${outputForm.mokuhyoSeirenHyoji}" />に必要なスピナの期待値：
<font color="red"><b>
    <fmt:formatNumber maxFractionDigits="0" value="${outputForm.kitaitiSpina0toS}"/>
</b>スピナ</font>
</p>
<p>
<h3>期待値の明細</h3>
<p>
<b>精錬値ごとのスピナの期待値、試行回数の期待値、各鉱石の使用数の期待値を以下に示します。</b>
</p>
<table border="1">
<c:forEach var="itemList" items="${outputForm.hyoujiItemList}">
    <tr align="center"  bgcolor="#c0ffc0">
        <th width="10%">精錬値</th>
        <th width="10%">成功率</th>
        <c:forEach var="item" items="${itemList}">
            <th width="20%"><c:out value="${item}" /></th>
        </c:forEach>
    </tr>
    <tr align="center" bgcolor="#ffffdb">
        <td width="10%" ><c:out value="${outputForm.seirenStr}" /></th>
        <td width="10%">-</th>
        <c:forEach var="item" items="${itemList}">
            <td width="20%">
                <fmt:formatNumber maxFractionDigits="2" value="${outputForm.customUseMap[item]}"/>
            </td>
        </c:forEach>
    </tr>
    <c:forEach var="seiren" items="${outputForm.hyoujiDataList}"  varStatus="status">
    <tr align="center">
        <td><c:out value="${seiren}" /></td>
        <td><c:out value="${outputForm.seikouRateList[status.index]}%" /></td>
        <c:forEach var="item" items="${itemList}">
            <td>
                <fmt:formatNumber maxFractionDigits="2" value="${outputForm.kitaitiMap[item][status.index]}"/>
            </td>
        </c:forEach>
    </tr>
    </c:forEach>
</c:forEach>
</table>
  </c:param>
</c:import>
