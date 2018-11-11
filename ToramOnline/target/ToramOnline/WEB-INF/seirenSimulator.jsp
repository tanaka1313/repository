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
<h3>
精錬値<c:out value="${outputForm.syokiSeirenHyoji}" />から精錬値<c:out value="${outputForm.mokuhyoSeirenHyoji}" />になるまで精錬する試行を<c:out value="${outputForm.doNum}" />回行った結果・・・
</h3>
</p>
<p>
<h4>消費スピナの明細</h4>
<font size="+1">
<table>
    <c:forEach var="spn" items="${outputForm.spnList}" varStatus="status">
        <tr align="right"><td>
            <fmt:formatNumber maxFractionDigits="0" value="${spn}"/>スピナ
        </td></tr>
    </c:forEach>
</table>
</font>
<p>
   <h4>平均値</h4>
<font size="+1">
<table>
    <tr align="right"><td>
        <fmt:formatNumber maxFractionDigits="0" value="${outputForm.average}"/>スピナ
    </td></tr>
</table></font>
    <h4>標準偏差</h4>
<font size="+1">
<table>
    <tr align="right"><td>
        <fmt:formatNumber maxFractionDigits="0" value="${outputForm.standardDev}"/>スピナ
    </td></tr>
</table></font>
</p>
<p>
<font color="red"><c:out value="${outputForm.errMessage}" /></font>
</p>
  </c:param>
</c:import>
