# Independent audit acceptance: vanilla `/tp` correction

Signed by: `/root/c_raw_pitch_setter_1211_1212_audit`, independent source reviewer.

## Bound inputs

- Original author report, preserved unchanged: `c-raw-pitch-setter-1211-1212-source/report.md`, SHA-256 `FEE374A8B55EC931727265EDAD321CF8CD4E6C67F8D31B7C3F1F79AE9DC9D97E`.
- Author correction: `c-raw-pitch-setter-1211-1212-source/correction.md`, SHA-256 `0CC1E0142DBD74DAC3339CF97AD4626D8ECE0A8EBACFB113EB166BADAB1FC134`.
- Exact actual audit verdict file: `c-raw-pitch-setter-1211-1212-audit/verdict.md`, SHA-256 `245811BADBE89FBF2DBE2F47CC3270B1B052B95415DE1B76B96FCDBBB800B7EA`.

The three file hashes were independently recomputed before this acceptance. The 61-character hash string mentioned as a typo inside the correction is not used as an identity; this acceptance binds to the full 64-hex verdict hash above.

## Acceptance

Accepted, bounded to the vanilla command-to-client pitch route. The correction accurately identifies that 1.21.1 `ServerPlayer.teleportTo` overrides the base entity route and passes the wrapped command pitch to the network listener; the listener's server-side `absMoveTo` clamp does not change the raw pitch argument in the outgoing packet, and the 1.21.1 client handler applies that argument through the direct-store setter. The 1.21.2 `ServerPlayer` path calls the base teleport method, which clamps before constructing the transition sent to the client. The specified `/tp ... 200` absolute pitch example therefore exposes `-160` to the 1.21.1 local setter and `-90` to the 1.21.2 local setter. This corrects the original report's blanket statement that vanilla map `/tp` cannot supply out-of-range pitch to the client setter.

The correction separately states that this end-to-end route also changes server-side teleport behavior and must not be attributed solely to the client setter change. I accept that qualification. It does not extend or change the separately accepted ordinary mouse movement ownership/reachability assumptions: the mouse path remains the clean client-side witness, and the teleport route remains a distinct server-mediated witness. The original report is unmodified. No runtime behavior or a particular map's use of `/tp` is inferred.
