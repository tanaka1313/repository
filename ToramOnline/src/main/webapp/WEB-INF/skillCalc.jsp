<c:import url="layout.jsp">
  <c:param name="title" value="スキル必要ポイントの計算" />
  <c:param name="content">
<h2>スキル必要ポイントの計算</h2>
<p>
スキル取得時の必要スキルポイントの計算を行います。
</p>
<p><font color="red"><b>実行結果のログから統計情報を集計し表示するように修正しました。<br />
詳細は<a href="https://tanaka1313.hatenablog.com/entry/2018/10/20/110809" target="blank">アップデートの詳細</a> を参照してください。</b></font>
</p>
<p>
「計算する」ボタンを押下すると、実行結果を表示し、ログの取得を行います。
</p>
<h3>統計情報について</h3>
<p>
収集したログをもとに統計情報を表示します。統計情報はリアルタイムでは更新されず、手動で更新しています。<br />
<b>統計情報の更新日：2018/10/20(土)</b><br />
(収集期間：2018/9/28～2018/10/20)
</p>
<p>
統計情報を表示するには、武器種を選択してから「統計更新」ボタンを押下してください。
</p>
<form:form modelAttribute="inputForm" action="#output">
<p>
以下の項目で「許可しない」を選択すればログの取得を行いません。<br />
実行結果のログ取得を<form:select path="permitLog" items="${permitLogItems}" /><br />
<b><font color="red">特に理由がなければログ取得を許可していただければ幸いです。</font></b>
</p>
<a href="#output" >実行結果へ</a><br />
<c:forEach var="skillGroup" items="${inputForm.skillGroup}" varStatus="status">
<a name="group${status.index}"></a>
<font size="+1">
    <h3><c:out value="${inputForm.skillGroup[status.index].skillGroupName}" /></h3>
<p>
<span style="display:inline-block;width:358px;">
スキル情報：
<c:if test="${!empty inputForm.skillGroup[status.index].wikiUrl}">
    <a href="${inputForm.skillGroup[status.index].wikiUrl}"　target="_blank">Wiki</a>
</c:if>
<c:if test="${empty inputForm.skillGroup[status.index].wikiUrl}">
    Wiki
</c:if>, 
<c:if test="${!empty inputForm.skillGroup[status.index].wikiwikiUrl}">
    <a href="${inputForm.skillGroup[status.index].wikiwikiUrl}" target="_blank">WikiWiki</a>
</c:if>
<c:if test="${empty inputForm.skillGroup[status.index].wikiwikiUrl}">
    WikiWiki
</c:if>
<c:if test="${!empty inputForm.skillGroup[status.index].sonotaUrl}">
    , <a href="${inputForm.skillGroup[status.index].sonotaUrl}" target="_blank">その他</a>
</c:if>
</span>
<font size="-1">統計情報 武器種<form:select path="statisticsBuki" items="${statisticsBukiItems}" /></font>
<form:button formaction="#group${status.index}" type="submit" name="statisticsSet" >統計更新</form:button>
</p>
<form:hidden path="skillGroup[${status.index}].skillGroupName"></form:hidden>
<form:hidden path="skillGroup[${status.index}].wikiUrl"></form:hidden>
<form:hidden path="skillGroup[${status.index}].wikiwikiUrl"></form:hidden>

<ul id="tv${status.index}" class="filetree">
<li>
    <span style="display:inline-block;width:452px;"><c:out value="${skillGroup.skillGroupName}" /></span>
    <font size="-1">
    <span style="display:inline-block;width:50px;">取得率</span>
    <span style="display:inline-block;width:170px;">Lv頻度(上位2位)</span>
    </font>
<ul>
<c:forEach var="skillName" items="${skillGroup.skillDataList}" varStatus="nameStatus">
    <li><span class="skill skill1" style="display:inline-block;">
            <span style="display:inline-block;width:244px;">
            <c:out value="${skillName.skillName}" />
            <form:hidden path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].skillName"></form:hidden>  
            <form:hidden path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].skillCode"></form:hidden>  
            </span>
    </span>
            <form:select id="code${skillName.skillCode}" path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].skillLv" items="${lvItems}"></form:select>
            <span style="display:inline-block;width:2px;"></span>
            <input type="button" id="button10_${skillName.skillCode}" value="10" />
            <span style="display:inline-block;width:2px;"></span>
            <input type="button" id="button1_${skillName.skillCode}" value="1 " />
            <font size="-1">
            <span style="display:inline-block;width:50px;">
                <fmt:formatNumber maxFractionDigits="0" value="${skillName.acquisitionRate}"/>
                <c:if test="${!empty skillName.acquisitionRate}">%</c:if>
            </span>
            <span style="display:inline-block;width:170px;">
                <c:out value="${skillName.lvFrequency}" />
            </span>
            </font>
            <form:hidden path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].acquisitionRate"></form:hidden>  
            <form:hidden path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].lvFrequency"></form:hidden>  
    <c:if test="${skillName.skillDataList.size() > 0}">
        <ul>
    </c:if>
    <c:forEach var="skillName2" items="${skillName.skillDataList}" varStatus="nameStatus2">
        <li><span class="skill skill2" style="display:inline-block;">
            <span style="display:inline-block;width:228px;">
            <c:out value="${skillName2.skillName}" />
            <form:hidden path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].skillDataList[${nameStatus2.index}].skillName"></form:hidden>  
            <form:hidden path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].skillDataList[${nameStatus2.index}].skillCode"></form:hidden>  
            </span>
        </span>
            <form:select id="code${skillName2.skillCode}" path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].skillDataList[${nameStatus2.index}].skillLv" items="${lvItems}"></form:select>
            <span style="display:inline-block;width:2px;"></span>
            <input type="button" id="button10_${skillName2.skillCode}" value="10" />
            <span style="display:inline-block;width:2px;"></span>
            <input type="button" id="button1_${skillName2.skillCode}" value="1 " />
            <font size="-1">
            <span style="display:inline-block;width:50px;">
                <fmt:formatNumber maxFractionDigits="0" value="${skillName2.acquisitionRate}"/>
                <c:if test="${!empty skillName2.acquisitionRate}">%</c:if>
            </span>
            <span style="display:inline-block;width:170px;">
                <c:out value="${skillName2.lvFrequency}" />
            </span>
            </font>
            <form:hidden path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].skillDataList[${nameStatus2.index}].acquisitionRate"></form:hidden>  
            <form:hidden path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].skillDataList[${nameStatus2.index}].lvFrequency"></form:hidden>  
        <c:if test="${skillName2.skillDataList.size() > 0}">
            <ul>
        </c:if>
        <c:forEach var="skillName3" items="${skillName2.skillDataList}" varStatus="nameStatus3">
            <li><span class="skill skill3" style="display:inline-block;">
                <span style="display:inline-block;width:212px;">
                <c:out value="${skillName3.skillName}" />
                <form:hidden path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].skillDataList[${nameStatus2.index}].skillDataList[${nameStatus3.index}].skillName"></form:hidden>  
                <form:hidden path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].skillDataList[${nameStatus2.index}].skillDataList[${nameStatus3.index}].skillCode"></form:hidden>  
                </span>
            </span>
                <form:select id="code${skillName3.skillCode}" path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].skillDataList[${nameStatus2.index}].skillDataList[${nameStatus3.index}].skillLv" items="${lvItems}"></form:select>
                <span style="display:inline-block;width:2px;"></span>
                <input type="button" id="button10_${skillName3.skillCode}" value="10" />
                <span style="display:inline-block;width:2px;"></span>
                <input type="button" id="button1_${skillName3.skillCode}" value="1 " />
                <font size="-1">
                <span style="display:inline-block;width:50px;">
                    <fmt:formatNumber maxFractionDigits="0" value="${skillName3.acquisitionRate}"/>
                    <c:if test="${!empty skillName3.acquisitionRate}">%</c:if>
                </span>
                <span style="display:inline-block;width:170px;">
                    <c:out value="${skillName3.lvFrequency}" />
                </span>
                </font>
                <form:hidden path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].skillDataList[${nameStatus2.index}].skillDataList[${nameStatus3.index}].acquisitionRate"></form:hidden>  
                <form:hidden path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].skillDataList[${nameStatus2.index}].skillDataList[${nameStatus3.index}].lvFrequency"></form:hidden>  
            <c:if test="${skillName3.skillDataList.size() > 0}">
                <ul>
            </c:if>
            <c:forEach var="skillName4" items="${skillName3.skillDataList}" varStatus="nameStatus4">
                <li><span class="skill skill4" style="display:inline-block;">
                    <span style="display:inline-block;width:196px;">
                    <c:out value="${skillName4.skillName}" />
                    <form:hidden path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].skillDataList[${nameStatus2.index}].skillDataList[${nameStatus3.index}].skillDataList[${nameStatus4.index}].skillName"></form:hidden>  
                    <form:hidden path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].skillDataList[${nameStatus2.index}].skillDataList[${nameStatus3.index}].skillDataList[${nameStatus4.index}].skillCode"></form:hidden>  
                    </span>
                </span>
                    <form:select id="code${skillName4.skillCode}" path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].skillDataList[${nameStatus2.index}].skillDataList[${nameStatus3.index}].skillDataList[${nameStatus4.index}].skillLv" items="${lvItems}"></form:select>
                    <span style="display:inline-block;width:2px;"></span>
                    <input type="button" id="button10_${skillName4.skillCode}" value="10" />
                    <span style="display:inline-block;width:2px;"></span>
                    <input type="button" id="button1_${skillName4.skillCode}" value="1 " />
                    <font size="-1">
                    <span style="display:inline-block;width:50px;">
                        <fmt:formatNumber maxFractionDigits="0" value="${skillName4.acquisitionRate}"/>
                        <c:if test="${!empty skillName4.acquisitionRate}">%</c:if>
                    </span>
                    <span style="display:inline-block;width:170px;">
                        <c:out value="${skillName4.lvFrequency}" />
                    </span>
                    </font>
                    <form:hidden path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].skillDataList[${nameStatus2.index}].skillDataList[${nameStatus3.index}].skillDataList[${nameStatus4.index}].acquisitionRate"></form:hidden>  
                    <form:hidden path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].skillDataList[${nameStatus2.index}].skillDataList[${nameStatus3.index}].skillDataList[${nameStatus4.index}].lvFrequency"></form:hidden>  
                <c:if test="${skillName4.skillDataList.size() > 0}">
                    <ul>
                </c:if>
                <c:forEach var="skillName5" items="${skillName4.skillDataList}" varStatus="nameStatus5">
                    <li><span class="skill skill5" style="display:inline-block;">
                        <span style="display:inline-block;width:180px;">
                        <c:out value="${skillName5.skillName}" />
                        <form:hidden path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].skillDataList[${nameStatus2.index}].skillDataList[${nameStatus3.index}].skillDataList[${nameStatus4.index}].skillDataList[${nameStatus5.index}].skillName"></form:hidden>  
                        <form:hidden path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].skillDataList[${nameStatus2.index}].skillDataList[${nameStatus3.index}].skillDataList[${nameStatus4.index}].skillDataList[${nameStatus5.index}].skillCode"></form:hidden>  
                        </span>
                    </span>
                        <form:select id="code${skillName5.skillCode}" path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].skillDataList[${nameStatus2.index}].skillDataList[${nameStatus3.index}].skillDataList[${nameStatus4.index}].skillDataList[${nameStatus5.index}].skillLv" items="${lvItems}"></form:select>
                        <span style="display:inline-block;width:2px;"></span>
                        <input type="button" id="button10_${skillName5.skillCode}" value="10" />
                        <span style="display:inline-block;width:2px;"></span>
                        <input type="button" id="button1_${skillName5.skillCode}" value="1 " />
                        <font size="-1">
                        <span style="display:inline-block;width:50px;">
                            <fmt:formatNumber maxFractionDigits="0" value="${skillName5.acquisitionRate}"/>
                            <c:if test="${!empty skillName5.acquisitionRate}">%</c:if>
                        </span>
                        <span style="display:inline-block;width:170px;">
                            <c:out value="${skillName5.lvFrequency}" />
                        </span>
                        </font>
                        <form:hidden path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].skillDataList[${nameStatus2.index}].skillDataList[${nameStatus3.index}].skillDataList[${nameStatus4.index}].skillDataList[${nameStatus5.index}].acquisitionRate"></form:hidden>  
                        <form:hidden path="skillGroup[${status.index}].skillDataList[${nameStatus.index}].skillDataList[${nameStatus2.index}].skillDataList[${nameStatus3.index}].skillDataList[${nameStatus4.index}].skillDataList[${nameStatus5.index}].lvFrequency"></form:hidden>  
                        
        
                    </li>
                </c:forEach>
                <c:if test="${skillName4.skillDataList.size() > 0}">
                    </ul>
                </c:if>
        
                </li>
            </c:forEach>
            <c:if test="${skillName3.skillDataList.size() > 0}">
                </ul>
            </c:if>
        
            </li>
        </c:forEach>
        <c:if test="${skillName2.skillDataList.size() > 0}">
            </ul>
        </c:if>
        
        </li>
    </c:forEach>
    <c:if test="${skillName.skillDataList.size() > 0}">
        </ul>
    </c:if>
    </li>
    
    
</c:forEach>
</ul>
</li>
</ul>
</font>
<form:button type="submit" name="sendData"  style="background-color:#f3f3f3;height:35px;-webkit-appearance:none;">計算する</form:button>  
<a href="#output" >実行結果へ</a><br /><br />
</c:forEach>
</form:form>
<h2>実行結果</h2>
<a name="output"></a>
<font color="red"><c:out value="${outputForm.skillPt}" /><br /></font>
<br />
<b>取得スキル一覧</b>
<ul id="outputtv" class="filetree">
    <c:forEach var="resultSkill" items="${outputForm.resultString}" varStatus="status">
        <li><span class="skill skill1">
                <span style="display:inline-block;width:80px;"></span>
                <c:out value="${resultSkill}" />
            </span>
            <c:if test="${resultSkill.skillDataList.size() > 0}"><ul></c:if>
        <c:forEach var="resultSkill2" items="${resultSkill.skillDataList}" varStatus="status2">
            <li>
                <span class="skill skill2">
                    <span style="display:inline-block;width:64px;"></span>
                    <c:out value="${resultSkill2}" />
                </span>
                
            <c:if test="${resultSkill2.skillDataList.size() > 0}"><ul></c:if>
            <c:forEach var="resultSkill3" items="${resultSkill2.skillDataList}" varStatus="status3">
                <li>
                    <span class="skill skill3">
                        <span style="display:inline-block;width:48px;"></span>
                        <c:out value="${resultSkill3}" />
                    </span>
                <c:if test="${resultSkill3.skillDataList.size() > 0}"><ul></c:if>
                <c:forEach var="resultSkill4" items="${resultSkill3.skillDataList}" varStatus="status4">
                    <li>
                        <span class="skill skill4">
                            <span style="display:inline-block;width:32px;"></span>
                            <c:out value="${resultSkill4}" />
                        </span>
                    <c:if test="${resultSkill4.skillDataList.size() > 0}"><ul></c:if>
                    <c:forEach var="resultSkill5" items="${resultSkill4.skillDataList}" varStatus="status5">
                        <li>
                            <span class="skill skill5">
                                <span style="display:inline-block;width:16px;"></span>
                                <c:out value="${resultSkill5}" />
                            </span>
                            
                        </li>
                    </c:forEach>
                    <c:if test="${resultSkill4.skillDataList.size() > 0}"></ul></c:if>
                        
                    </li>
                </c:forEach>
                <c:if test="${resultSkill3.skillDataList.size() > 0}"></ul></c:if>
                    
                </li>
            </c:forEach>
            <c:if test="${resultSkill2.skillDataList.size() > 0}"></ul></c:if>
            
            </li>
        </c:forEach>
        <c:if test="${resultSkill.skillDataList.size() > 0}"></ul></c:if>
        </li>
    </c:forEach>
</ul>
<script type="text/javascript">
    $(document).ready(function(){
    	<c:forEach var="skillGroup" items="${inputForm.skillGroup}" varStatus="status">
                $("#tv${status.index}").treeview();
        </c:forEach>
        $("#outputtv").treeview();
    });
</script>
<script type="text/javascript">

	$(window).load(function() {
		<c:forEach var="skillGroup" items="${inputForm.skillGroup}">
		<c:forEach var="skillData" items="${skillGroup.skillDataList}">
            $('#button1_${skillData.skillCode}').click(function() {
                document.getElementById("code${skillData.skillCode}").value = 1;
                lvCheck('${skillData.skillCode}');
            });
            $('#button5_${skillData.skillCode}').click(function() {
                document.getElementById("code${skillData.skillCode}").value = 5;
                lvCheck('${skillData.skillCode}');
            });
            $('#button10_${skillData.skillCode}').click(function() {
                document.getElementById("code${skillData.skillCode}").value = 10;
                lvCheck('${skillData.skillCode}');
            });
		<c:forEach var="skillData2" items="${skillData.skillDataList}">
		    $('#code${skillData2.skillCode}').change(function() {
	    		lvCheck('${skillData2.skillCode}');
    		});
            $('#button1_${skillData2.skillCode}').click(function() {
                document.getElementById("code${skillData2.skillCode}").value = 1;
                lvCheck('${skillData2.skillCode}');
            });
            $('#button5_${skillData2.skillCode}').click(function() {
                document.getElementById("code${skillData2.skillCode}").value = 5;
                lvCheck('${skillData2.skillCode}');
            });
            $('#button10_${skillData2.skillCode}').click(function() {
                document.getElementById("code${skillData2.skillCode}").value = 10;
                lvCheck('${skillData2.skillCode}');
            });
        <c:forEach var="skillData3" items="${skillData2.skillDataList}">
		    $('#code${skillData3.skillCode}').change(function() {
	    		lvCheck('${skillData3.skillCode}');
    		});
            $('#button1_${skillData3.skillCode}').click(function() {
                document.getElementById("code${skillData3.skillCode}").value = 1;
                lvCheck('${skillData3.skillCode}');
            });
            $('#button5_${skillData3.skillCode}').click(function() {
                document.getElementById("code${skillData3.skillCode}").value = 5;
                lvCheck('${skillData3.skillCode}');
            });
            $('#button10_${skillData3.skillCode}').click(function() {
                document.getElementById("code${skillData3.skillCode}").value = 10;
                lvCheck('${skillData3.skillCode}');
            });
		<c:forEach var="skillData4" items="${skillData3.skillDataList}">
		    $('#code${skillData4.skillCode}').change(function() {
	    		lvCheck('${skillData4.skillCode}');
    		});
            $('#button1_${skillData4.skillCode}').click(function() {
                document.getElementById("code${skillData4.skillCode}").value = 1;
                lvCheck('${skillData4.skillCode}');
            });
            $('#button5_${skillData4.skillCode}').click(function() {
                document.getElementById("code${skillData4.skillCode}").value = 5;
                lvCheck('${skillData4.skillCode}');
            });
            $('#button10_${skillData4.skillCode}').click(function() {
                document.getElementById("code${skillData4.skillCode}").value = 10;
                lvCheck('${skillData4.skillCode}');
            });
		<c:forEach var="skillData5" items="${skillData4.skillDataList}">
	    	$('#code${skillData5.skillCode}').change(function() {
	    		lvCheck('${skillData5.skillCode}');
	    	});
            $('#button1_${skillData5.skillCode}').click(function() {
            	document.getElementById("code${skillData5.skillCode}").value = 1;
                lvCheck('${skillData5.skillCode}');
            });
            $('#button5_${skillData5.skillCode}').click(function() {
                document.getElementById("code${skillData5.skillCode}").value = 5;
                lvCheck('${skillData5.skillCode}');
            });
            $('#button10_${skillData5.skillCode}').click(function() {
                document.getElementById("code${skillData5.skillCode}").value = 10;
                lvCheck('${skillData5.skillCode}');
            });
		</c:forEach>
		</c:forEach>
		</c:forEach>
		</c:forEach>
		</c:forEach>
		</c:forEach>
	})
	function lvCheck(skillCode) {
		skillCode = skillCode + "";
		var length = skillCode.length;
		skillCode = skillCode.substring(0, length);
		var skillLv = document.getElementById("code" + skillCode).value;
		var length = skillCode.length;
		var i = 0;
		if (skillLv > 0 && length > 2) {
			for (i = length; i > 2; i--) {
				var zenteiCode;
				zenteiCode = skillCode.substring(0, i - 1);
				var zenteiLv = document.getElementById("code" + zenteiCode).value;
				if (zenteiLv < 5) {
					document.getElementById("code" + zenteiCode).value = 5;
				}
			}
		}
	}
</script>

  </c:param>
</c:import>
