# Phase-two report and identity comparison

## Frozen inputs and continuity

This addendum reads only the two authorized artifacts below. Their on-disk SHA-256 values match the assigned literals exactly:

- `c-raw-pitch-setter-1206-121-source/report.md`: `331d0c572e31ae77461f03119179db70c3299031d0f498120361fe34c66da21d`
- `c-raw-pitch-setter-1206-121-source/identity.md`: `99afb5171ab5488df54c9053edb3afc12b10cf8045b039d7bcf0f4ecf32bf575`

`identity.md` explicitly binds its correction to the exact `report.md` SHA above and says it supplements, rather than changes, that report. This establishes exact document-level continuity. Neither supplied artifact carries a signer key or other independently verifiable person/agent identity, so this comparison does not authenticate a real-world or cryptographic writer identity. No author lookup was performed.

The prior independent verdict remains a separate immutable byte sequence, SHA-256 `ccff33799a6fa109a5a886b734c47dafdd1aa2f3ec7be0315bf1c9f1a23f2264`. This addendum does not modify or replace it.

## Bounded comparison and verdict

The bound report's technical result—no difference in the 1.20.6→1.21.0 ordinary mouse pitch path—is consistent with the prior frozen audit. Both identify the same essential route and operations: accumulated mouse deltas, the shared sensitivity branches, `Entity.turn`'s first pitch setter call followed by the outer clamp setter call, and the conditional fall-flying pitch consumer. The prior audit additionally froze and matched hashes for `Options`, `SmoothDouble`, `LocalPlayer`, and `LivingEntity`; those extra source identities are not asserted by the bound report's shorter source-hash list.

`identity.md` correctly repairs the bound report's 1.20.6 evidence caveat. The report's observation that a nested `.decompile-provenance.json` and current raw cache directory were absent is compatible with canonical provenance being recorded at the 1.20.6 version root. The source side's `mojmap.ready.json`, `mojmap.provenance.json`, source manifest, and artifact manifest supply that identity; the ready marker binds the source and artifact manifest hashes, and the cited source files match the source manifest. Therefore the report's conclusion that 1.20.6 input/mapping provenance was not verified is superseded by the exact root-sidecar evidence described in `identity.md` and independently included in the prior frozen verdict. The absent raw cache means current raw bytes were not re-hashed; it does not invalidate the exact version-root source identity or its recorded input pins.

**Verdict: ACCEPT the bounded no-difference source conclusion and the corrected 1.20.6 provenance statement, bound to the exact report and identity artifact hashes above.** This closes only this narrow source-identity correction; it says nothing about full-pair movement coverage, implementation, or runtime parity.

## Attestation boundary

I attest as the assigned independent phase-two reviewer that this addendum is limited to the two literal-hash-verified lab inputs and the independently frozen first-verdict identity. This is an authorship statement by the current agent, not a keyed digital signature. The detached SHA-256 of this addendum is the integrity identity to retain with the handoff.