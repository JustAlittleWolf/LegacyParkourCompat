# Input identity correction

This correction is an addendum to the original verdict. It changes only the frozen C comparison's machine-readable SHA-256 field; the reviewed file, source evidence, findings, scope, and verdict remain unchanged.

## Machine binding

```json
{
  "record": "input-identity-correction",
  "original_verdict": {
    "path": "C:/Users/Wolfi/.codex/visualizations/2026/11/18/01a1f01a-5064-75c0-a594-b2e7fa3b99e2/c-mounted-direct-response-audit/verdict.md",
    "sha256": "f57c12677a9eec6f20cd6c7d45de81c679ee3f246804bcb962aaf9c55e835eff"
  },
  "corrected_input": {
    "role": "frozen_C_comparison",
    "path": "C:/Users/Wolfi/.codex/visualizations/2026/11/18/01a1f01a-5064-75c0-a594-b2e7fa3b99e2/c-mounted-direct-response/comparison.md",
    "sha256": "93c97731a8945a4bddae1011f877e54334df0c502c42f5a2d708a2b9210b474e"
  },
  "retained_prior_input": {
    "role": "prior_B_source_audit",
    "path": "C:/Users/Wolfi/.codex/visualizations/2026/11/18/01a1f01a-693d-7972-bfe2-805d6db43b98/domain-b-mounted-clip-source/source-audit.md",
    "sha256": "b5a38759285eaea0dc60dbd2803e445356a8a0f07b0246269af4309ceed509d5"
  },
  "scope_change": false
}
```

The comparison digest above was recomputed from the exact bound file path. The original verdict's comparison SHA field was malformed; this record preserves its original bytes and supplies the verified input identity as an addendum.

## Source-pin erratum (no source or scope change)

The immutable B source audit contains two contextual source-hash typos; the C verdict repeats the 26.2 `Entity.java` typo. Canonical hashes were recomputed from the exact repository files below. The `Boat.java` source is context-only; its pin does not support or alter any audited claim. No immutable B or C document was edited.

```json
{
  "record": "source-pin-erratum",
  "terminal": "COMPLETED",
  "immutable_bindings": {
    "B_source_audit": {
      "path": "C:/Users/Wolfi/.codex/visualizations/2026/11/18/01a1f01a-693d-7972-bfe2-805d6db43b98/domain-b-mounted-clip-source/source-audit.md",
      "sha256": "b5a38759285eaea0dc60dbd2803e445356a8a0f07b0246269af4309ceed509d5"
    },
    "C_original_verdict": {
      "path": "C:/Users/Wolfi/.codex/visualizations/2026/11/18/01a1f01a-5064-75c0-a594-b2e7fa3b99e2/c-mounted-direct-response-audit/verdict.md",
      "sha256": "f57c12677a9eec6f20cd6c7d45de81c679ee3f246804bcb962aaf9c55e835eff"
    },
    "previous_input_identity_correction_revision_sha256": "7df9a80fdb68c76c072563b9965111166fd3022068a8f026ee60fd89e8974a1e"
  },
  "entity_java": {
    "role": "audited_source",
    "path": "D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/entity/Entity.java",
    "canonical_sha256": "7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5",
    "B_manifest_pin": "7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dfaff869bb811d5",
    "C_verdict_pin": "7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dfaff869bb811d5",
    "pin_matches_canonical": false
  },
  "boat_java": {
    "role": "context_only",
    "path": "D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated/net/minecraft/world/entity/vehicle/boat/Boat.java",
    "canonical_sha256": "b4ad2291573e1e352fc24ab0b337d252c67cf6158ca3d49acf97d75a94cf2840",
    "B_manifest_pin": "b4ad2291573e1e352fc24ab0b337d252c67cf6158ca3d49acfc97d75a94cf2840",
    "pin_matches_canonical": false
  },
  "scope_change": false
}
```
