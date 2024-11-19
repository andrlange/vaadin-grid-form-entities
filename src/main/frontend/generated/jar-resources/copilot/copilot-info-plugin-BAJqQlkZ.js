import { a as k, $ as D, y as b, R as E, B as $, e as f, u as c, a0 as w, G as S, F as A, W as T, M as R, b as P, l as C } from "./copilot-BpTSGORU.js";
import { r as I } from "./state-B_WfFdFb.js";
import { B as H } from "./base-panel-DGNBZPed.js";
import { showNotification as N } from "./copilot-notification-DOfexahU.js";
import { i as m } from "./icons-DT_AreNR.js";
const U = "copilot-info-panel{--dev-tools-red-color: red;--dev-tools-grey-color: gray;--dev-tools-green-color: green;position:relative}copilot-info-panel div.info-tray{display:flex;flex-direction:column;gap:10px}copilot-info-panel vaadin-button{margin-inline:var(--lumo-space-l)}copilot-info-panel dl{display:grid;grid-template-columns:auto auto;gap:0;margin:var(--space-100) var(--space-50);font:var(--font-xsmall)}copilot-info-panel dl>dt,copilot-info-panel dl>dd{padding:3px 10px;margin:0;white-space:nowrap;overflow:hidden;text-overflow:ellipsis}copilot-info-panel dd.live-reload-status>span{overflow:hidden;text-overflow:ellipsis;display:block;color:var(--status-color)}copilot-info-panel dd span.hidden{display:none}copilot-info-panel dd span.true{color:var(--dev-tools-green-color);font-size:large}copilot-info-panel dd span.false{color:var(--dev-tools-red-color);font-size:large}copilot-info-panel code{white-space:nowrap;-webkit-user-select:all;user-select:all}copilot-info-panel .checks{display:inline-grid;grid-template-columns:auto 1fr;gap:var(--space-50)}copilot-info-panel span.hint{font-size:var(--font-size-0);background:var(--gray-50);padding:var(--space-75);border-radius:var(--radius-2)}";
var B = function() {
  var e = document.getSelection();
  if (!e.rangeCount)
    return function() {
    };
  for (var t = document.activeElement, o = [], s = 0; s < e.rangeCount; s++)
    o.push(e.getRangeAt(s));
  switch (t.tagName.toUpperCase()) {
    case "INPUT":
    case "TEXTAREA":
      t.blur();
      break;
    default:
      t = null;
      break;
  }
  return e.removeAllRanges(), function() {
    e.type === "Caret" && e.removeAllRanges(), e.rangeCount || o.forEach(function(r) {
      e.addRange(r);
    }), t && t.focus();
  };
}, j = B, h = {
  "text/plain": "Text",
  "text/html": "Url",
  default: "Text"
}, L = "Copy to clipboard: #{key}, Enter";
function O(e) {
  var t = (/mac os x/i.test(navigator.userAgent) ? "⌘" : "Ctrl") + "+C";
  return e.replace(/#{\s*key\s*}/g, t);
}
function W(e, t) {
  var o, s, r, n, l, a, d = !1;
  t || (t = {}), o = t.debug || !1;
  try {
    r = j(), n = document.createRange(), l = document.getSelection(), a = document.createElement("span"), a.textContent = e, a.ariaHidden = "true", a.style.all = "unset", a.style.position = "fixed", a.style.top = 0, a.style.clip = "rect(0, 0, 0, 0)", a.style.whiteSpace = "pre", a.style.webkitUserSelect = "text", a.style.MozUserSelect = "text", a.style.msUserSelect = "text", a.style.userSelect = "text", a.addEventListener("copy", function(i) {
      if (i.stopPropagation(), t.format)
        if (i.preventDefault(), typeof i.clipboardData > "u") {
          o && console.warn("unable to use e.clipboardData"), o && console.warn("trying IE specific stuff"), window.clipboardData.clearData();
          var y = h[t.format] || h.default;
          window.clipboardData.setData(y, e);
        } else
          i.clipboardData.clearData(), i.clipboardData.setData(t.format, e);
      t.onCopy && (i.preventDefault(), t.onCopy(i.clipboardData));
    }), document.body.appendChild(a), n.selectNodeContents(a), l.addRange(n);
    var p = document.execCommand("copy");
    if (!p)
      throw new Error("copy command was unsuccessful");
    d = !0;
  } catch (i) {
    o && console.error("unable to copy using execCommand: ", i), o && console.warn("trying IE specific stuff");
    try {
      window.clipboardData.setData(t.format || "text", e), t.onCopy && t.onCopy(window.clipboardData), d = !0;
    } catch (y) {
      o && console.error("unable to copy using clipboardData: ", y), o && console.error("falling back to prompt"), s = O("message" in t ? t.message : L), window.prompt(s, e);
    }
  } finally {
    l && (typeof l.removeRange == "function" ? l.removeRange(n) : l.removeAllRanges()), a && document.body.removeChild(a), r();
  }
  return d;
}
var F = W;
const J = /* @__PURE__ */ k(F);
var M = Object.defineProperty, V = Object.getOwnPropertyDescriptor, g = (e, t, o, s) => {
  for (var r = s > 1 ? void 0 : s ? V(t, o) : t, n = e.length - 1, l; n >= 0; n--)
    (l = e[n]) && (r = (s ? l(t, o, r) : l(r)) || r);
  return s && r && M(t, o, r), r;
};
let v = class extends H {
  constructor() {
    super(...arguments), this.serverInfo = [], this.clientInfo = [{ name: "Browser", version: navigator.userAgent }], this.handleServerInfoEvent = (e) => {
      const t = JSON.parse(e.data.info);
      this.serverInfo = t.versions, D().then((o) => {
        o && (this.clientInfo.unshift({ name: "Vaadin Employee", version: "true", more: void 0 }), this.requestUpdate("clientInfo"));
      }), b() === "success" && E("hotswap-active", { value: $() });
    };
  }
  connectedCallback() {
    super.connectedCallback(), this.onCommand("copilot-info", this.handleServerInfoEvent), this.onEventBus("system-info-with-callback", (e) => {
      e.detail.callback(this.getInfoForClipboard(e.detail.notify));
    }), this.reaction(
      () => f.idePluginState,
      () => {
        this.requestUpdate("serverInfo");
      }
    );
  }
  getIndex(e) {
    return this.serverInfo.findIndex((t) => t.name === e);
  }
  render() {
    return c`<style>
        ${U}
      </style>
      <div class="info-tray">
        <dl>
          ${[...this.serverInfo, ...this.clientInfo].map(
      (e) => c`
              <dt>${e.name}</dt>
              <dd title="${e.version}" style="${e.name === "Java Hotswap" ? "white-space: normal" : ""}">
                ${this.renderValue(e.version)} ${e.more}
              </dd>
            `
    )}
          ${this.renderDevWorkflowSection()}
        </dl>
        ${this.renderDevelopmentWorkflowButton()}
      </div>`;
  }
  renderDevWorkflowSection() {
    const e = b(), t = this.getIdePluginLabelText(f.idePluginState), o = this.getHotswapAgentLabelText(e);
    return c`
      <dt>Java Hotswap</dt>
      <dd>${u(e === "success")} ${o}</dd>
      <dt>IDE Plugin</dt>
      <dd>${u(w() === "success")} ${t}</dd>
    `;
  }
  renderDevelopmentWorkflowButton() {
    const e = S();
    let t = "", o = null;
    return e.status === "success" ? (t = "More details...", o = m.successColorful) : e.status === "warning" ? (t = "Improve Development Workflow...", o = m.warningColorful) : e.status === "error" && (t = "Fix Development Workflow...", o = c`<span style="color: var(--red)">${m.error}</span>`), c`
      <vaadin-button
        id="development-workflow-guide"
        @click="${() => {
      A();
    }}">
        <span slot="prefix"> ${o}</span>
        ${t}</vaadin-button
      >
    `;
  }
  getHotswapAgentLabelText(e) {
    return e === "success" ? "Java Hotswap is enabled" : e === "error" ? "Hotswap is partially enabled" : "Hotswap is not enabled";
  }
  getIdePluginLabelText(e) {
    if (w() !== "success")
      return "Not installed";
    if (e?.version) {
      let t = null;
      return e?.ide && (e?.ide === "intellij" ? t = "IntelliJ" : e?.ide === "vscode" ? t = "VS Code" : e?.ide === "eclipse" && (t = "Eclipse")), t ? `${e?.version} ${t}` : e?.version;
    }
    return "Not installed";
  }
  renderValue(e) {
    return e === "false" ? u(!1) : e === "true" ? u(!0) : e;
  }
  getInfoForClipboard(e) {
    const t = this.renderRoot.querySelectorAll(".info-tray dt"), r = Array.from(t).map((n) => ({
      key: n.textContent.trim(),
      value: n.nextElementSibling.textContent.trim()
    })).filter((n) => n.key !== "Live reload").filter((n) => !n.key.startsWith("Vaadin Emplo")).map((n) => {
      const { key: l } = n;
      let { value: a } = n;
      if (l === "IDE Plugin")
        a = this.getIdePluginLabelText(f.idePluginState) ?? "false";
      else if (l === "Java Hotswap") {
        const d = f.jdkInfo?.jrebel, p = b();
        d && p === "success" ? a = "JRebel is in use" : a = this.getHotswapAgentLabelText(p);
      }
      return `${l}: ${a}`;
    }).join(`
`);
    return e && N({
      type: T.INFORMATION,
      message: "Environment information copied to clipboard",
      dismissId: "versionInfoCopied"
    }), r.trim();
  }
};
g([
  I()
], v.prototype, "serverInfo", 2);
g([
  I()
], v.prototype, "clientInfo", 2);
v = g([
  C("copilot-info-panel")
], v);
let x = class extends R {
  createRenderRoot() {
    return this;
  }
  connectedCallback() {
    super.connectedCallback(), this.style.display = "flex";
  }
  render() {
    return c`<button title="Copy to clipboard" aria-label="Copy to clipboard" theme="icon tertiary">
      <span
        @click=${() => {
      P.emit("system-info-with-callback", {
        callback: J,
        notify: !0
      });
    }}
        >${m.copy}</span
      >
    </button>`;
  }
};
x = g([
  C("copilot-info-actions")
], x);
const _ = {
  header: "Info",
  expanded: !1,
  panelOrder: 15,
  panel: "right",
  floating: !1,
  tag: "copilot-info-panel",
  actionsTag: "copilot-info-actions",
  eager: !0
  // Render even when collapsed as error handling depends on this
}, z = {
  init(e) {
    e.addPanel(_);
  }
};
window.Vaadin.copilot.plugins.push(z);
function u(e) {
  return e ? c`<span class="true">☑</span>` : c`<span class="false">☒</span>`;
}
export {
  x as Actions,
  v as CopilotInfoPanel
};
