const _ = Symbol.for("react.portal"), g = Symbol.for("react.fragment"), S = Symbol.for("react.strict_mode"), I = Symbol.for("react.profiler"), E = Symbol.for("react.provider"), C = Symbol.for("react.context"), d = Symbol.for("react.forward_ref"), T = Symbol.for("react.suspense"), N = Symbol.for("react.suspense_list"), F = Symbol.for("react.memo"), R = Symbol.for("react.lazy");
function h(e, t, n) {
  const r = e.displayName;
  if (r)
    return r;
  const o = t.displayName || t.name || "";
  return o !== "" ? `${n}(${o})` : n;
}
function f(e) {
  return e.displayName || "Context";
}
function i(e) {
  if (e == null)
    return null;
  if (typeof e == "function")
    return e.displayName || e.name || null;
  if (typeof e == "string")
    return e;
  switch (e) {
    case g:
      return "Fragment";
    case _:
      return "Portal";
    case I:
      return "Profiler";
    case S:
      return "StrictMode";
    case T:
      return "Suspense";
    case N:
      return "SuspenseList";
  }
  if (typeof e == "object")
    switch (e.$$typeof) {
      case C:
        return `${f(e)}.Consumer`;
      case E:
        return `${f(e._context)}.Provider`;
      case d:
        return h(e, e.render, "ForwardRef");
      case F:
        const t = e.displayName || null;
        return t !== null ? t : i(e.type) || "Memo";
      case R: {
        const n = e, r = n._payload, o = n._init;
        try {
          return i(o(r));
        } catch {
          return null;
        }
      }
    }
  return null;
}
let s;
function K() {
  const e = /* @__PURE__ */ new Set();
  return Array.from(document.body.querySelectorAll("*")).flatMap(A).filter(v).filter((n) => !n.fileName.endsWith("frontend/generated/flow/Flow.tsx")).forEach((n) => e.add(n.fileName)), Array.from(e);
}
function v(e) {
  return !!e && e.fileName;
}
function w(e) {
  return e?._debugSource || void 0;
}
function P(e) {
  if (e && e.type?.__debugSourceDefine)
    return e.type.__debugSourceDefine;
}
function A(e) {
  return w(p(e));
}
function b() {
  return `__reactFiber$${m()}`;
}
function O() {
  return `__reactContainer$${m()}`;
}
function m() {
  if (!(!s && (s = Array.from(document.querySelectorAll("*")).flatMap((e) => Object.keys(e)).filter((e) => e.startsWith("__reactFiber$")).map((e) => e.replace("__reactFiber$", "")).find((e) => e), !s)))
    return s;
}
function $(e) {
  const t = e.type;
  return t?.$$typeof === d && !t.displayName && e.child ? $(e.child) : i(e.type) ?? i(e.elementType) ?? "???";
}
function L() {
  const e = Array.from(document.querySelectorAll("body > *")).flatMap((n) => n[O()]).find((n) => n), t = c(e);
  return c(t?.child);
}
function Y(e) {
  const t = [];
  let n = c(e.child);
  for (; n; )
    t.push(n), n = c(n.sibling);
  return t;
}
const j = (e) => {
  const t = Y(e);
  if (t.length === 0)
    return [];
  const n = t.filter((r) => D(r) || U(r));
  return n.length === t.length ? t : t.flatMap((r) => n.includes(r) ? r : j(r));
};
function M(e) {
  return e.hasOwnProperty("entanglements") && e.hasOwnProperty("containerInfo");
}
function x(e) {
  return e.hasOwnProperty("stateNode") && e.hasOwnProperty("pendingProps");
}
function c(e) {
  const t = e?.stateNode;
  if (t?.current && (M(t) || x(t)))
    return t?.current;
  if (!e)
    return;
  if (!e.alternate)
    return e;
  const n = e.alternate, r = e?.actualStartTime, o = n?.actualStartTime;
  return o !== r && o > r ? n : e;
}
function p(e) {
  const t = b(), n = c(e[t]);
  if (n?._debugSource)
    return n;
  let r = n?.return || void 0;
  for (; r && !r._debugSource; )
    r = r.return || void 0;
  return r;
}
function y(e) {
  if (e.stateNode?.isConnected === !0)
    return e.stateNode;
  if (e.child)
    return y(e.child);
}
function D(e) {
  const t = y(e);
  return t && c(p(t)) === e;
}
function U(e) {
  return typeof e.type != "function" ? !1 : !!(e._debugSource || P(e));
}
function W(e) {
  return e === void 0 ? !1 : e.nodeId >= 0;
}
function k(e) {
  if (e.javaClass)
    return e.javaClass.substring(e.javaClass.lastIndexOf(".") + 1);
}
function q(e) {
  const t = window.Vaadin;
  if (t && t.Flow) {
    const { clients: n } = t.Flow, r = Object.keys(n);
    for (const o of r) {
      const a = n[o];
      if (a.getNodeId) {
        const u = a.getNodeId(e);
        if (u >= 0) {
          const l = a.getNodeInfo(u);
          return { nodeId: u, uiId: a.getUIId(), element: e, javaClass: l.javaClass, styles: l.styles };
        }
      }
    }
  }
}
function V() {
  const e = window.Vaadin;
  let t;
  if (e && e.Flow) {
    const { clients: n } = e.Flow, r = Object.keys(n);
    for (const o of r) {
      const a = n[o];
      a.getUIId && (t = a.getUIId());
    }
  }
  return t;
}
function z(e) {
  return {
    uiId: e.uiId,
    nodeId: e.nodeId
  };
}
function G(e) {
  return e ? e.type?.type === "FlowContainer" : !1;
}
function J(e) {
  return e.localName.startsWith("flow-container");
}
export {
  P as a,
  z as b,
  G as c,
  y as d,
  p as e,
  D as f,
  w as g,
  j as h,
  W as i,
  $ as j,
  q as k,
  k as l,
  L as m,
  c as n,
  J as o,
  K as p,
  V as q
};
