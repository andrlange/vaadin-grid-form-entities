import { u as p, R as c, W as g, l as f } from "./copilot-BpTSGORU.js";
import { r as u } from "./state-B_WfFdFb.js";
import { B as h } from "./base-panel-DGNBZPed.js";
import { showNotification as m } from "./copilot-notification-DOfexahU.js";
import { i as v } from "./icons-DT_AreNR.js";
const b = "copilot-features-panel{padding:var(--space-100);font:var(--font-xsmall);display:grid;grid-template-columns:auto 1fr;gap:var(--space-50);height:auto}copilot-features-panel a{display:flex;align-items:center;gap:var(--space-50);white-space:nowrap}copilot-features-panel a svg{height:12px;width:12px;min-height:12px;min-width:12px}";
var F = Object.defineProperty, w = Object.getOwnPropertyDescriptor, d = (e, t, a, o) => {
  for (var s = o > 1 ? void 0 : o ? w(t, a) : t, r = e.length - 1, l; r >= 0; r--)
    (l = e[r]) && (s = (o ? l(t, a, s) : l(s)) || s);
  return o && s && F(t, a, s), s;
};
const n = window.Vaadin.devTools;
let i = class extends h {
  constructor() {
    super(...arguments), this.features = [], this.handleFeatureFlags = (e) => {
      this.features = e.data.features;
    };
  }
  connectedCallback() {
    super.connectedCallback(), this.onCommand("featureFlags", this.handleFeatureFlags);
  }
  render() {
    return p` <style>
        ${b}
      </style>
      ${this.features.map(
      (e) => p`
          <copilot-toggle-button
            .title="${e.title}"
            ?checked=${e.enabled}
            @on-change=${(t) => this.toggleFeatureFlag(t, e)}>
          </copilot-toggle-button>
          <a class="ahreflike" href="${e.moreInfoLink}" title="Learn more" target="_blank"
            >learn more ${v.linkExternal}</a
          >
        `
    )}`;
  }
  toggleFeatureFlag(e, t) {
    const a = e.target.checked;
    c("use-feature", { source: "toggle", enabled: a, id: t.id }), n.frontendConnection ? (n.frontendConnection.send("setFeature", { featureId: t.id, enabled: a }), m({
      type: g.INFORMATION,
      message: `“${t.title}” ${a ? "enabled" : "disabled"}`,
      details: t.requiresServerRestart ? "This feature requires a server restart" : void 0,
      dismissId: `feature${t.id}${a ? "Enabled" : "Disabled"}`
    })) : n.log("error", `Unable to toggle feature ${t.title}: No server connection available`);
  }
};
d([
  u()
], i.prototype, "features", 2);
i = d([
  f("copilot-features-panel")
], i);
const $ = {
  header: "Features",
  expanded: !1,
  panelOrder: 35,
  panel: "right",
  floating: !1,
  tag: "copilot-features-panel",
  helpUrl: "https://vaadin.com/docs/latest/flow/configuration/feature-flags"
}, x = {
  init(e) {
    e.addPanel($);
  }
};
window.Vaadin.copilot.plugins.push(x);
export {
  i as CopilotFeaturesPanel
};
