<c:import url="layout.jsp">
  <c:param name="title" value="装備強化シミュレータ" />
  <c:param name="content">
<h2>装備強化シミュレータ</h2>
<p>
任意のプロパ付与手順について、成功率や消費潜在などを計算します。
</p>
<p>
装備強化の前提、コツ、ロジック詳細が知りたい方は <b><a href="<c:url value="/ProperLogic" />" >こちら</a></b>。
</p>
<p>
対象レベル、強化部位を最初に設定し、レベル更新を押してください。<br />
レベル更新を押さなければ、選択肢が更新されません。
</p>
<form:form modelAttribute="inputForm">
    強化部位<form:select path="properBui" items="${buiItems}" /><br />
    対象レベル<form:select path="paramLevel" items="${levelItems}" />
    <form:button type="submit" name="lebelUpdate">レベル更新</form:button><br />
    <h3>プロパ</h3>
    <c:forEach var="pItem" items="${inputForm.properList}" varStatus="status">
        プロパ名<form:select path="properList[${status.index}].properName" >
            <form:options items="${plusItems}" />
        </form:select><br />
    </c:forEach>
    ※プラスプロパ、マイナスプロパの両方を選択し、プロパ決定ボタンを押下してください。
    <h3>初期潜在など</h3>
    初期潜在値<form:select path="shokiSenzai" items="${senzaiItems}" /><br />
    基礎潜在値(任意)<form:select path="kisoSenzai" items="${senzaiItems}" /><br />
    <form:button type="submit" name="setProper"  style="background-color:#f3f3f3;height:35px;-webkit-appearance:none;">プロパ決定</form:button><br />

<div>
<h3>付与手順の設定</h3>
<a href="#output" >実行結果へ</a><br />
<c:forEach var="step" items="${inputForm.properStepList}" varStatus="status">
<a name="step${status.index}"></a>
<div class="box6">
    <b>手順<c:out value="${status.index + 1}" /></b><br />
    <table>
    <tr>
    <td valign="top">
    <table>
        <tr>
         <th>プロパ</th>
         <th>レベル  </th>
         <th>変更値</th>
        </tr>
        <c:forEach var="proper" items="${step.properList}" varStatus="sts">
            <tr>
             <td>
                <c:out value="${proper.properName}" />
                <form:hidden path="properStepList[${status.index}].properList[${sts.index}].properName" />
             </td>
             <td><form:select path="properStepList[${status.index}].properList[${sts.index}].properLv" items="${properLvItems}" /></td>
             <td><c:out value="${proper.vProperSabun}" /></td>
            </tr>
        </c:forEach>
    </table>
    </td>
    <td></td>
    <td valign="top">
    <table border="1" cellspacing="0" bordercolor="#000000">
        <tr bgcolor="#c0ffc0">
            <th align="left">ペナルティ</th>
            <th align="left">消費潜在</th>
        </tr>
        <tr>
            <td><c:out value="${step.vPenalty}" /></td>
            <td><c:out value="${step.vUseSenzai}" /></td>
        </tr>
        <tr bgcolor="#c0ffc0">
            <th align="left">付与前潜在</th>
            <th align="left">付与後潜在</th>
        </tr>
        <tr>
            <td><c:out value="${step.iBeforeSenzai}" /></td>
            <td><c:out value="${step.iAfterSenzai}" /></td>
        </tr>
        <tr bgcolor="#c0ffc0">
            <th align="left">成功率</th>
            <th></th>
        </tr>
        <tr>
            <td>
                <font color="red"><fmt:formatNumber maxFractionDigits="1" value="${step.dSeikouRate}"/>%</font>
            </td>
            <td></td>
        </tr>
    </table>
    </td>
    </tr>
    </table>
    <table>
        <tr>
            <td width="30%"><form:radiobutton path="properStepList[${status.index}].matometeStr" label="まとめて付与" value="matomete" /></td>
            <td width="30%"><form:radiobutton path="properStepList[${status.index}].matometeStr" label="1ずつ付与" value="tandoku" /></td>
            <td width="30%"><form:button formaction="#step${status.index}" type="submit" name="calcurate"  style="background-color:#f3f3f3;height:35px;-webkit-appearance:none;">計算</form:button></td>
        </tr>
    </table>
    <c:forEach var="error" items="${step.warnStrList}" varStatus="sts">
        <font color="red" ><c:out value="${error}" /></font><br />
    </c:forEach>
    <c:out value="${step.vSozaiData}" /><br />
    <table>
        <tr>
            <td>
                <form:button formaction="#step${status.index}" type="submit" name="insertAfter" value="${status.index}"  style="background-color:#f3f3f3;height:35px;-webkit-appearance:none;">後に手順追加</form:button>
            </td>
            <td>
                <form:button formaction="#step${status.index}" type="submit" name="insertBefore" value="${status.index}"  style="background-color:#f3f3f3;height:35px;-webkit-appearance:none;">前に手順追加</form:button>
            </td>
            <td>
                <form:button formaction="#step${status.index}" type="submit" name="delete" value="${status.index}"  style="background-color:#f3f3f3;height:35px;-webkit-appearance:none;">この手順を削除</form:button>
            </td>
        </tr>
    </table>
</div>
</c:forEach>
<form:button formaction="#output" type="submit" name="calcurate"  style="background-color:#f3f3f3;height:35px;-webkit-appearance:none;">実行結果を計算する</form:button>
</div>
</form:form>
<div>
<h3>実行結果</h3>
<a name="output"></a>
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
<c:if test="${outputForm.seikou < 1}">
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
</c:if>
  </c:param>
</c:import>
