# Renewed independent source verdict

## Verdict

**ACCEPT** the revised residual report, bound to SHA-256 `d2e657d79ecc8b8fceab9d3b3e0ef56026515eddf4b90a5a343cac9496919406` (`.native-source/20261030/1152-1165-finish43/leaves-liquid-provider-residual/report.md`). The report's unchanged `source-hashes.tsv` SHA-256 is `A157F147D8FEF6BC92C9659FCEF6AFC66E1190216B9B4BD917D1890E65897A45`; all 13 source hashes were verified during the prior review. The additional verifier-source ledger is `reviewer-source-hashes.tsv`, SHA-256 `739589450151F9203AB263208ABCA07964B814DA29B3CB5083C96613F7847821`.

## Renewal basis

The prior review `review.md` (SHA-256 `CA3F5DB05DD4199A5F80CEFF9961FF80EB205B6B8176FF61FB6B91A2B7E74EE0`) requested citation corrections only. The revised report now cites 1.15.2 `Block.isFaceSturdy` at lines 419–421, `canSupportCenter` at 413–416, and `canSupportRigidBlock` at 405–411; it cites 1.16.5 `LiquidBlock.STABLE_SHAPE` at line 34 and `getCollisionShape` at lines 51–57. These locations match the pinned source files.

The revised tag statement also explicitly bounds the dependency: with equal effective LEAVES membership, tagged-at-both closes this support result, while untagged-at-both can expose old full collision support versus new empty support shape. Removal only at 1.16.5 does not establish the delta. The reviewed fluid-flow consumer and local-player current route remain concrete under the stated falling-fluid, fluid-overlap, and non-flying gates. The removed lava callback remains covered by the cited pool33 occupancy/height evidence, and LocalPlayer still cannot satisfy `canStandOnFluid` for the new stable-shape gate.

The previous REVISE verdict remains bound only to its original report hash; this renewal applies only to the revised hash above. No product, wiki, backlog, build, test, game launch, or decompilation work was performed.
