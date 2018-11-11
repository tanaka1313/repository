<c:import url="layout.jsp">
  <c:param name="title" value="${param.titleData}" />
  <c:param name="content">
  <div>
      ${param.contentFirst}
  </div>
<p>
<b><font color="red">計算式や劣化率などは十分に検証できていないため、間違っている可能性があります。
自己責任で使用してください。</font></b>
</p>
<p>
計算式や成功率の算出方法などは<a href="<c:url value="/SeirenLogic" />" > こちら </a>をご確認ください。
</p>

<form:form modelAttribute="inputForm">
<c:forEach var="pItem" items="${inputForm.hozonTankaItems.keySet()}" varStatus="status">
    <form:hidden path="hozonTankaItems[${pItem}]" />
</c:forEach>
<p>
    <h3>①デフォルト値を適用する場合はボタンを押してください。</h3>
    ※ただし管理人はデフォルト値に対して責任は持ちません。適宜カスタマイズしてください。<br />
    <form:button type="submit" name="setDefaultNashi" style="background-color:#f3f3f3;height:35px;-webkit-appearance:none;">スロなしデフォルト設定を適用</form:button>
    <form:button type="submit" name="setDefaultAri"  style="background-color:#f3f3f3;height:35px;-webkit-appearance:none;" >スロありデフォルト設定を適用</form:button><br />
 </p>
<p>
    <h3>②劣化率（精錬失敗時に強化値が下がる確率）を設定してください。</h3>
    ※デフォルト値には管理人が予想した値を設定してます。適宜カスタマイズしてください。<br />
    <font size="+1"><table border="1">
    <tr>
        <th>パラ種類</th><th>劣化なし</th><th>1劣化</th><th>2劣化</th><th>3劣化</th>
    </tr>
    <tr>
        <td>
            <c:out value="${inputForm.tecRekka.vParamName}" />
        </td>
        <td>
            <form:input path="tecRekka.vRekkaNashi" size="7" />%
        </td>
        <td>
            <form:input path="tecRekka.vRekka1" size="7" />%
        </td>
        <td>
            <form:input path="tecRekka.vRekka2" size="7" />%
        </td>
        <td>
            <form:input path="tecRekka.vRekka3" size="7" />%
        </td>
    </tr>
    <tr>
        <td>
            <c:out value="${inputForm.lucRekka.vParamName}" />
        </td>
        <td>
            <form:input path="lucRekka.vRekkaNashi" size="7" />%
        </td>
        <td>
            <form:input path="lucRekka.vRekka1" size="7" />%
        </td>
        <td>
            <form:input path="lucRekka.vRekka2" size="7" />%
        </td>
        <td>
            <form:input path="lucRekka.vRekka3" size="7" />%
        </td>
    </tr>
    </table></font>
    ※補助アイテムによる劣化防止率を加味しない割合を記載してください。<br />
    ※総和が100でない場合は自動で総和が100となるように調整されます。<br />
    ※分数の入力可能 (四則 +,-,*,/ の使用が可能)
</p>
<p>
<h3>③鉱石と補助アイテムを選択してください。</h3>
<font size="+1"><table>
<tr>
    <th>精錬値</th>
    <th>使用パラ</th>
    <th>使用鉱石</th>
    <th>補助アイテム</th>
</tr>
<c:forEach var="pItem" items="${inputForm.seirenItems}" varStatus="status">
<tr>
    <td><c:out value="${inputForm.seirenItems[status.index].vHyoujiData}" /></td>
    <td><form:select path="seirenItems[${status.index}].vParamName" items="${inputForm.paramItems}" /></td>
    <td><form:select path="seirenItems[${status.index}].vUseKouseki" items="${inputForm.kousekiItems}" /></td>
    <td><form:select path="seirenItems[${status.index}].vUseHojoItem" items="${inputForm.hojoitemItems}" /></td>
</tr>
</c:forEach>
</table></font>
※補助アイテムの括弧内： (劣化防止率 / 成功率+〇% / 最終成功率の加算値)

</p>
<p>
<h3>④必要スピナの計算のため、単価の設定が必要です。「単価設定」ボタンを押して下さい。</h3>
    <form:button type="submit" name="tankaData"  style="background-color:#f3f3f3;height:35px;-webkit-appearance:none;">単価設定</form:button><br />
        ※新しい補助アイテムを設定した場合は押下してください。
</p>
<p>
<h3>⑤消費アイテムの単価を設定してください。</h3>
<font size="+1"><table>
<tr>
    <th>消費アイテム</th>
    <th>単価</th>
</tr>
<c:forEach var="pItem" items="${inputForm.tankaItems}" varStatus="status">
<tr>
    <td>
        <c:out value="${inputForm.tankaItems[status.index].vItemName}" />
        <form:hidden path="tankaItems[${status.index}].vItemName" />
    </td>
    <td><form:input path="tankaItems[${status.index}].iTanka" /></td>
</tr>
</c:forEach>
</table></font>
</p>
<p>
<h3>⑥精錬値の初期値と目標値を設定してください。</h3>
<font size="+1"><table>
    <tr><td>初期値： <form:select path="syokiSeiren" items="${seirenItemsSyoki}"/></td></tr>
    <tr><td>目標値： <form:select path="mokuhyoSeiren" items="${seirenItemsMokuhyo}" /></td></tr>
</table></font>
</p>
<p>
    <form:button formaction="#output" type="submit" name="sendData" style="background-color:#f3f3f3;height:35px;-webkit-appearance:none;" >期待値を求める</form:button>  
    <form:button formaction="#output" type="submit" name="sendSimu" style="background-color:#f3f3f3;height:35px;-webkit-appearance:none;" >10回シミュレートする</form:button>
</p>
</form:form>

<div>
    ${param.contentEnd}
</div>
  </c:param>
</c:import>
