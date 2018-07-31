<div class="container-fluid xd-container">
	<h1>讨论区</h1>
	<div class="container-fluid xd-container">
		<!-- 畅言评论框 start -->
		<div class="comment">
			<div id="SOHUCS" sid="http://eureka.likegirl.cn" ></div>
		</div>
		<!-- 畅言评论框 end -->
	</div>
</div>

<!-- changyan 公共JS代码 start (一个网页只需插入一次) -->
<script type="text/javascript">
	(function () {
		var appid = "cytvkXGcm";
		var conf = "prod_0bf835b056444b00e3a270bdba2dcb1d";
		var width = window.innerWidth || document.documentElement.clientWidth;
		if (width < 960) {
			window.document.write('<script id="changyan_mobile_js" charset="utf-8" type="text/javascript" src="http://changyan.sohu.com/upload/mobile/wap-js/changyan_mobile.js?client_id=' + appid + '&conf=' + conf + '"><\/script>');
		} else {
			var loadJs = function (d, a) {
				var c = document.getElementsByTagName("head")[0] || document.head || document.documentElement;
				var b = document.createElement("script");
				b.setAttribute("type", "text/javascript");
				b.setAttribute("charset", "UTF-8");
				b.setAttribute("src", d);
				if (typeof a === "function") {
					if (window.attachEvent) {
						b.onreadystatechange = function () {
							var e = b.readyState;
							if (e === "loaded" || e === "complete") {
								b.onreadystatechange = null;
								a()
							}
						}
					} else {
						b.onload = a
					}
				}
				c.appendChild(b)
			};
			loadJs("http://changyan.sohu.com/upload/changyan.js", function () {
				window.changyan.api.config({appid: appid, conf: conf})
			});
		}
	})(); 
</script>
<!-- changyan 公共JS代码 end -->


