# Independent acceptance — swim-pitch gate correction

**Scoped reviewer:** `/root/c_swim_pitch_vertical_1217_1218_audit` (independent source auditor). **Signed:** Codex subagent `/root/c_swim_pitch_vertical_1217_1218_audit`, 2026-11-18.

## Immutable inputs

- Original source report: `c-swim-pitch-vertical-1217-1218-source/report.md`, SHA-256 `EA02CB51CD7F723FAC3233C09717CF2F9F22D3AC1328C66A3278B5EFDF805F7B`.
- Original independent audit verdict: `c-swim-pitch-vertical-1217-1218-audit/verdict.md`, SHA-256 `5FC67256E5C87ED98B6AD3A88A9DAFFF03BBAB1105685695CF9D0142C8F5A9A5`.
- Signed source correction: `c-swim-pitch-vertical-1217-1218-source/correction.md`, SHA-256 `A17B470640E721F353533FA278A2D659A959161AA6D7E3A6F0F43A6854FA1FFD`.

All three byte identities were checked before reading the correction. The report and prior verdict remain unchanged.

## Acceptance

**Accepted for the specific prior objection.** The correction accurately states the third `Player.travel` gate disjunct in both releases: `!fluidState.isEmpty()` makes the condition pass when the sampled fluid state is nonempty. If look-Y is positive and jumping is false, an empty sampled state does not pass the gate; if look-Y is nonpositive or jumping is true, either earlier OR disjunct passes regardless of that fluid state. The original report's “fluid state is empty” phrasing was inverted, and the correction closes that factual issue.

I independently rechecked 1.21.7 Mojmap `Player.java:1445-1449`; it contains the negated `isEmpty()` test and performs the Y velocity write only under the enclosing condition. The entire 1.21.7 and 1.21.8 `Player.java` byte hashes are both `8DC5514FE44311F39268692F5188840B9F65CB71143228FA8B84242585EA7987`, confirming the same gate in the compared source files. The correction also keeps the pre-super sampling order and bounded no-difference conclusion intact, consistent with the bound report and verdict.

This acceptance closes only the inverted-gate-description objection for these exact bound revisions. It does not expand the audited operation, establish full-pair coverage, set an intermediate patch boundary, or assert runtime parity.
