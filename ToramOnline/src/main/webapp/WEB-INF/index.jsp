<c:import url="layout.jsp">
  <c:param name="title" value="田中の怠惰なトーラム攻略情報TOP"/>
  <c:param name="content">
        <h2>更新履歴</h2>
        <p>
		  <a href="https://tanaka1313.hatenablog.com/">更新情報ブログ</a>に最新の更新情報を載せていくようにしました。<br />
		</p>
		<p>
		  更新情報は上記ブログをご確認ください。<br />
		  リリース直後はバグが発生する可能性があるため、バグがありそうな場合は直近にリリースがなかったかどうかご確認ください。
		  明らかにバグがありそうな場合はTwitterの@TarnadaTorumまでご連絡いただければ幸いです。
		</p>

		<h2>制作者より</h2>
         <h3>当サイトについて</h3>
         <p>
         当サイトはスマホアプリ「トーラムオンライン（Toram Online)」の攻略サイトとして2018/7/01に開設しました。
         </p>
         <p>
         当サイトは「管理をしなくても有益な情報を提供する」をモットーに、更新頻度が低くても長い間使用することができる情報を提供することを目的としています。<br />
         </p>
         <p>
         手始めに装備強化のプロパ付与の手順を自動化するツールを作成しました。<br />
         下記に詳細を記載します。よければ活用していってください。
         </p>
         <h2>装備強化(武器/体装備)について</h2>
         <p>
            当サイトを作成したきっかけです。<br />
           任意のプロパを付与する手順と成功率を自動で算出します。<br />
     <b><a href="<c:url value="/BukiProper" />">装備強化(武器)</a></b><br />
     <b><a href="<c:url value="/BouguProper" />">装備強化(体装備)</a></b>
         </p>
         <p>
         ３つのメインロジックとそれを支える１０近くのサブロジックにより、最大に限りなく近いプロパ付与手順を提供します。<br />
        人は間違えますが、機械は間違えません。
         </p>
         <p>
         なお、クリ４種には当初は対応しておりませんでしたが、2018/9/23(日)にロジックを追加し、クリ４種についても対応いたしました。
         </p>
         <p>
         現在、対象レベル160についてだいたいが成功率 最大になっているかと思います。
         </p>
         <p>
         もし、成功率が最大となっていないものがありましたら、最大となる手順を添えて、Twitterの@TarnadaTorumまで
         お気軽にご連絡ください。<br />
         最大限の努力はさせていただきます。
         </p>
         <h2>装備強化シミュレータについて</h2>
         <p>
         装備強化の手順を手動で設定し、素材ポイントや成功率などを計算できるツールを作成しました。
         </p>
         <p>
     <b><a href="<c:url value="/ProperSimulator" />">装備強化シミュレータ</a></b>
        </p>
        <p>
            上記の手順自動算出の実行結果を手動で微調整することも可能です。<br />
            ご活用いただければ幸いです。
            </p>
         
         <h2>必要スキルPTの計算について</h2>
         <p>
         忘却などを使用した際に、どのスキルをとるか悩むかと思います。<br />
         当サイトでもスキル取得の際に必要なスキルポイントを計算するツールを作成しました。<br />
         ユーザビリティを意識し、なるべく使いやすく工夫したつもりです。<br />
         もしよければご活用いただければ幸いです。
         </p>
         <p>
         <b><a href="<c:url value='/Skill' />" >必要スキルPT計算</a></b>
         </p>
         <p>
         2018/9/13(木)にグラフィカルに前提スキルが確認できるように修正いたしました。
         </p>
         <h2>精錬シミュレータについて</h2>
         <p>
         石橋をたたいて渡る性格のため、精錬の期待値を自動算出するツールを作成しました。
         </p>
         <p>
         <b><a href="<c:url value='/Seiren' />" >精錬シミュレータ</a></b>
         </p>
         <p>
         劣化率を分数で設定可能であったり、補助アイテムを設定可能であったり、
         鉱石や補助アイテムの単価を自由に設定できるなど、高いカスタマイズ性を実現できているかと思います。
         </p>
         <p>
         よく分からないという方はデフォルト設定のままお使いください。
         </p>
         <h2>謝辞</h2>
         <p>
         最後に、このツールは<a href="https://www.dopr.net/toramonline-wiki">【アソビモ】トーラムオンライン攻略情報まとめ【wiki】</a>に載っている情報をもとに作成しました。<br />
         その情報がなければ作成できていなかったと思います。<br />
         有益な情報を提供してくださったことを、本当に感謝します。
         </p>
         <h2>INFORMATION</h2>
         <dl class="information">
            <dt><span>2018-07-01</span></dt>
            <dd>
               <div>
                  サイトを正式版として開設しました。<br />
               </div>
            </dd>
         </dl>
         <dl class="information">
            <dt><span>2018-07-03</span></dt>
            <dd>
               <div>
                  <a href="https://tanaka1313.hatenablog.com/">更新情報ブログ</a>に最新の更新情報を載せていくようにしました。<br />
               </div>
            </dd>
         </dl>
         <dl class="information">
            <dt><span>2018-07-29</span></dt>
            <dd>
               <div>
                  当サイトに追加ツールとして <a href="<c:url value="/Seiren" />" >精錬シミュレータ</a> を作成しました。<br />
               </div>
            </dd>
         </dl>
         <dl class="information">
            <dt><span>2018-08-31</span></dt>
            <dd>
               <div>
                  当サイトに追加ツールとして <a href="<c:url value="/ProperSimulator" />" >装備強化シミュレータ</a> を作成しました。<br />
               </div>
            </dd>
         </dl>
  </c:param>
</c:import>