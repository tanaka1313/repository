<c:import url="layout.jsp">
  <c:param name="title" value="装備強化('${inputForm.properBui}')" />
  <c:param name="content">
<h2>装備強化(<c:out value="${inputForm.properBui}" />)</h2>
<p>
任意のプロパ付与について、成功率がなるべく高くなる手順を計算します。
</p>
<p>
<b><font color="red" size="+1">2018/9/23(日)に、ペナルティ値の大きくなるクリ４種についても対応できるように、ロジックを追加しました。<br />
詳細は<a href="https://tanaka1313.hatenablog.com/entry/2018/09/23/204517" target="blank">アップデートの詳細</a> を参照してください。</font></b>
</p>
<p>
装備強化の前提、コツ、ロジック詳細が知りたい方は <b><a href="<c:url value="/ProperLogic" />" >こちら</a></b>。
</p>
<p>
対象レベルを最初に設定し、レベル更新を押してください。<br />
レベル更新を押さなければ、選択肢が更新されません。
</p>
<div>
    <span><font color="red"><c:out value="${outputForm.errMsg}" /></font></span>
    <span><font color="red"><c:out value="${outputForm.seikouRate}" /></font></span>
</div>
<form:form modelAttribute="inputForm">
    <form:hidden path="properBui" />
    対象レベル<form:select path="paramLevel" items="${levelItems}" /><form:button type="submit" name="lebelUpdate">レベル更新</form:button><br />
    <h3>プラスプロパ</h3><br />
    <c:forEach var="pItem" items="${inputForm.plusProperList}" varStatus="status">
        プロパ名<form:select path="plusProperList[${status.index}].properName" >
        <form:options items="${plusItems}" />
        </form:select><br />
        プロパレベル<form:select path="plusProperList[${status.index}].properLvHyoji" items="${properLvItems}" /><br />
    </c:forEach>
    <h3>マイナスプロパ</h3><br />
    <c:forEach var="mItem" items="${inputForm.minusProperList}" varStatus="status">
        プロパ名<form:select path="minusProperList[${status.index}].properName" items="${minusItems}" />
        <form:select path="minusProperList[${status.index}].properLvHyoji" items="${properLvItems}" />
        <br />
    </c:forEach>
    <h3>初期潜在など</h3>
    初期潜在値<form:select path="shokiSenzai" items="${senzaiItems}" /><br />
    基礎潜在値(任意)<form:select path="kisoSenzai" items="${senzaiItems}" /><br />
    <form:button formaction="#output" type="submit" name="sendData"  style="background-color:#f3f3f3;height:35px;-webkit-appearance:none;">送信</form:button><br />
</form:form>

<div>
<h3>実行結果</h3>
<a name="#output"></a>
    <font color="red"><c:out value="${outputForm.errMsg}" /></font><br />
<b>装備強化(<c:out value="${inputForm.properBui}" />)</b><br />
    <c:out value="${outputForm.huyoProperPlus}" /><br />
    <c:out value="${outputForm.huyoProperMinus}" /><br />
</div>
<div>
    消費素材pt<br />
    <c:out value="${outputForm.sozaiPt}" /><br />
</div>
<div>
    <span><font color="red"><c:out value="${outputForm.seikouRate}" /></font></span>
    付与手順<b><c:out value="${outputForm.syokiSenzai}" /></b><br />
    <c:forEach var="step" items="${outputForm.huyoStep}" varStatus="status">
        <c:out value="${status.index + 1}. ${step}" /><br /><br />
    </c:forEach>
</div>
<div>
<form:form modelAttribute="simulator" action="/ToramOnline/ProperSimulator">
    <form:hidden path="properBui" />
    <form:hidden path="paramLevel" items="${levelItems}" />
    <c:forEach var="pItem" items="${simulator.properList}" varStatus="status">
        <form:hidden path="properList[${status.index}].properName" />
    </c:forEach>
    <form:hidden path="shokiSenzai" />
    <form:hidden path="kisoSenzai"  />
<c:forEach var="step" items="${simulator.properStepList}" varStatus="status">
        <c:forEach var="proper" items="${step.properList}" varStatus="sts">
                <form:hidden path="properStepList[${status.index}].properList[${sts.index}].properName" />
                <form:hidden path="properStepList[${status.index}].properList[${sts.index}].properLv"  />
        </c:forEach>
    <form:hidden path="properStepList[${status.index}].matometeStr" />
</c:forEach>
<form:button type="submit" name="calcurate"  style="background-color:#f3f3f3;height:35px;-webkit-appearance:none;">手順を手動修正する</form:button>
</form:form>
</div>
<c:if test="${outputForm.seikou < 1}">
<div>
<h3>非確定強化について</h3>
<p>
<c:out value="${outputForm.seikouRate}" />は各プロパごとに抽選されます。<br />
<c:out value="${outputForm.plusNum}" />個のプラスプロパがすべて成功する確率は
<fmt:formatNumber maxFractionDigits="2" value="${outputForm.plusSeikou}"/>%
となります。
</p>
<p>
ただし、抽選に失敗してもプロパレベルが下がらず、そのまま付く場合があるため、例えばLUK極であれば上記より大きな成功率になります。
</p>
</div>
</c:if>
  </c:param>
</c:import>
