import { l as u, u as d, Q as g, J as e, x as h } from "./copilot-BpTSGORU.js";
import { B as f } from "./base-panel-DGNBZPed.js";
import { i as l } from "./icons-DT_AreNR.js";
const m = "copilot-shortcuts-panel{font:var(--font-xsmall);padding:var(--space-200);display:flex;flex-direction:column;gap:var(--space-50)}copilot-shortcuts-panel h3{font:var(--font-xsmall-strong);margin:0;padding:0}copilot-shortcuts-panel h3:not(:first-of-type){margin-top:var(--space-200)}copilot-shortcuts-panel ul{list-style:none;margin:0;padding:0 var(--space-50);display:flex;flex-direction:column}copilot-shortcuts-panel ul li{display:flex;align-items:center;gap:var(--space-150);padding:var(--space-75) 0}copilot-shortcuts-panel ul li:not(:last-of-type){border-bottom:1px dashed var(--border-color)}copilot-shortcuts-panel ul li svg{height:16px;width:16px}copilot-shortcuts-panel ul li .kbds{flex:1;text-align:right}copilot-shortcuts-panel kbd{display:inline-block;border-radius:var(--radius-1);border:1px solid var(--border-color);min-width:1em;min-height:1em;text-align:center;margin:0 .1em;padding:.25em;box-sizing:border-box;font-size:var(--font-size-1);font-family:var(--font-family);line-height:1}";
var $ = Object.defineProperty, b = Object.getOwnPropertyDescriptor, v = (i, s, n, a) => {
  for (var o = a > 1 ? void 0 : a ? b(s, n) : s, r = i.length - 1, p; r >= 0; r--)
    (p = i[r]) && (o = (a ? p(s, n, o) : p(o)) || o);
  return a && o && $(s, n, o), o;
};
let c = class extends f {
  render() {
    return d`<style>
        ${m}
      </style>
      <h3>Global</h3>
      <ul>
        <li>${l.vaadinLogo} Copilot ${t(e.toggleCopilot)}</li>
        <li>${l.terminal} Command window ${t(e.toggleCommandWindow)}</li>
        <li>${l.undo} Undo ${t(e.undo)}</li>
        <li>${l.redo} Redo ${t(e.redo)}</li>
      </ul>
      <h3>Selected component</h3>
      <ul>
        <li>${l.code} Go to source ${t(e.goToSource)}</li>
        <li>${l.copy} Copy ${t(e.copy)}</li>
        <li>${l.paste} Paste ${t(e.paste)}</li>
        <li>${l.duplicate} Duplicate ${t(e.duplicate)}</li>
        <li>${l.userUp} Select parent ${t(e.selectParent)}</li>
        <li>${l.userLeft} Select previous sibling ${t(e.selectPreviousSibling)}</li>
        <li>${l.userRight} Select first child / next sibling ${t(e.selectNextSibling)}</li>
        <li>${l.trash} Delete ${t(e.delete)}</li>
      </ul>`;
  }
};
c = v([
  u("copilot-shortcuts-panel")
], c);
function t(i) {
  return d`<span class="kbds">${g(i)}</span>`;
}
const x = h({
  header: "Keyboard Shortcuts",
  tag: "copilot-shortcuts-panel",
  width: 400,
  height: 550,
  floatingPosition: {
    top: 50,
    left: 50
  }
}), y = {
  init(i) {
    i.addPanel(x);
  }
};
window.Vaadin.copilot.plugins.push(y);
