# Correction acceptance audit — swim-pitch vertical operation

## Bound artifacts

- Original destination report, `c-swim-pitch-vertical-1132-262-destination-witness/report.md`: SHA-256 `676D98D08A99735C2CCA1C5E5642945D0D8806F1DEDC39AF741D5E4C00DED76D`.
- Independent source verdict including its explicitly post-freeze report-comparison addendum, `c-swim-pitch-vertical-1132-262-destination-audit/verdict.md`: SHA-256 `4ECA17C836A3D57C941FD6A155A5B5C494648047B4D0A916B4F9B0C1EC44580A`. The original frozen pre-report body has identity `DE1C1DBAEE53A496DDEC3CC350501D4387B2356F6EF396E8B35133AA4FD716A7`.
- Author correction, `c-swim-pitch-vertical-1132-262-destination-witness/correction.md`: SHA-256 `DD1749B13600DFCBB61EF595474618A510F84C310C1DC7DD28EF67907798B8EC`.

All three observed file hashes match the supplied literal identities. The correction explicitly binds itself to the report and final independent verdict above; it leaves the original report intact.

## Acceptance

**Accepted for the bounded source-audit objections.** The correction accurately retracts the report's general look-vector-helper source-equivalence assertion and distinguishes the report's unspecified-pitch setup from a numeric witness. It reproduces the independent legal-pitch counterexample with the same source operations and values: for pitch `-38.594970703125F`, old/new LUT indices `58511`/`58510`, look-Y floats `0.6237360835075378F`/`0.6238110065460205F`, and direct `velocityY` results `0.037424165010452266D`/`0.03742866039276123D` from zero. The computed modern-minus-old difference is `0.000004495382308962459D` before inherited movement. These data close both the concrete counterexample challenge and the broader equivalence objection.

The corrected positive claim is appropriately bounded: on the described ordinary, non-riding local swimming path, both player corrections use the same swim and OR-gate structure, fluid-sample coordinate, thresholds, double coefficients, and direct update ordering before inherited input acceleration and the real move. The correction does not erase the pitch-dependent lookup difference. Its revised conclusion therefore matches the exact source evidence.

## Remaining unproven scope

This acceptance does not establish equal results over the pitch domain; the accepted counterexample proves that equality is false in general. The author's unspecified-pitch concrete pool setup still does not establish its own numeric look-Y or direct velocity value, although the correction explicitly acknowledges that. No full tick output is reconstructed after input acceleration, collision, drag, gravity, and fluid handling. Other swim states, riding/passenger behavior, flying/spectator gates, neighboring-cell boundaries, all other movement operations, exact intermediate-release introduction boundaries, runtime parity, and full 1.13.2–26.2 pair coverage remain outside this bounded correction acceptance.

No product source or other report was changed. This acceptance file is the only file written for this audit step.
