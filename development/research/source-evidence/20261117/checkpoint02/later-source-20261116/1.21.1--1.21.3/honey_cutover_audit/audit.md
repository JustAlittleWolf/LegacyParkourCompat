# Independent Honey cutover source audit

## Immutable inputs reviewed

- `build/later-source-20261116/1.21.1--1.21.3/honey_cutover/report.md` SHA-256: `4B670E9AA83C65C1CE436E8FC25FF3A4AC877896E8B51665B72D06E33EE2DBC1`.
- `build/later-source-20261116/1.21.1--1.21.3/honey_cutover/identity-supplement.md` SHA-256: `E1B35709A2B3851BAE251F489EBE419FC34C35EBC8BAC0D9D3DA8BE5F62F7A8F`.
- Accepted Honey velocity-frame draft SHA-256 supplied for this audit: `39E7D16E59C7A1D328E8BFD3E3E0BAECADE9116EB84A55888528879FA94A993C`.
- Accepted callback-traversal draft SHA-256 supplied for this audit: `EFDA4A0ADAB9B0CAE2CDBCE0C443F0BC2949E8B6E2BF7D426C3C267233B9388D`.

## Independent source check

Inspected the exact Mojmap `HoneyBlock.java`, `Entity.java`, and callback caller in `LivingEntity.java` for 1.21.1, 1.21.2, and 1.21.3 before opening the two reports. Verified the source file hashes against each publication's source ledger: for 1.21.1 and 1.21.3, the root ready marker binds the ledger digest and the relevant ledger entries equal the current files; for 1.21.2, the full `.decompile-provenance.json` source map binds the relevant current files.

Identity evidence agrees with the reports:

- 1.21.1 root ready marker says version and metadata ID `1.21.1`, mapping `mojmap`; its source-ledger SHA-256 is `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`. The provenance sidecar names version `1.21.1`, namespace `mojmap`, and the corresponding Mojmap run.
- 1.21.2 full-manifest SHA-256 is `00F399407DC447737EA83E3E46562418F8C712F66E3F51CB85D46AFA2ECAC575`. Its inputs identify exact metadata `/1.21.2.json`, client jar, and official client mapping text; its source map matches the Honey, Entity, and LivingEntity files byte-for-byte.
- 1.21.3 root ready marker says version and metadata ID `1.21.3`, mapping `mojmap`; its source-ledger SHA-256 is `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`. Its provenance sidecar names version `1.21.3`, namespace `mojmap`, and the corresponding Mojmap run.

The relevant source hashes are:

| Release | `HoneyBlock.java` | `Entity.java` | `LivingEntity.java` |
|---|---|---|---|
| 1.21.1 | `FD9C67C31C34833BF442E81EC0B1647E9F9E9042F01B9CD63127D2F832D5D8FF` | `B81905C7879E2CC5C5063A41D865C4164AD919F156306705BE791D1017B99850` | `324A3EEE8496CAAB57CFAF5101EF576F1AE3C60C40E3857E96F35F3AF9A3A0D8` |
| 1.21.2 | `40BF4E5D55F44F7AEC9716A46B11CD6AD08ED861BE4D63A85544A1BFB595A71D` | `A93719C302A0381A972AF75EA360465E2E3551708DD07C34D4D40B7E5173C2B9` | `087390495F4FDFD14B9E12230E7AEA4FDC50913BB4D1D91119E72892881CFC52` |
| 1.21.3 | `40BF4E5D55F44F7AEC9716A46B11CD6AD08ED861BE4D63A85544A1BFB595A71D` | `A93719C302A0381A972AF75EA360465E2E3551708DD07C34D4D40B7E5173C2B9` | `087390495F4FDFD14B9E12230E7AEA4FDC50913BB4D1D91119E72892881CFC52` |

## Verdict by operation

### Honey slide vertical-velocity frame

**Confirmed, first present in inspected release 1.21.2.** In 1.21.1, `isSlidingDown` gates on raw `deltaMovement.y`; `doSlideMovement` compares raw y with `-0.13`, scales horizontal velocity using the raw y value, and sets y to `-0.05`. In 1.21.2, `getOldDeltaY(y) = y / 0.98F + 0.08` is used for the `-0.08` eligibility gate and the `-0.13` movement/scaling gate, while `getNewDeltaY(y) = (y - 0.08) * 0.98F` re-encodes the replacement `-0.05` value. The relevant HoneyBlock source is byte-identical in 1.21.2 and 1.21.3. This is an independently supported source difference; the stated operation and boundary are accurate.

### Entity block-contact traversal and callback timing

**Confirmed, first present in inspected release 1.21.2.** In 1.21.1, `Entity.move` calls `tryCheckInsideBlocks()` before block-speed-factor application. The checker enumerates the entity's current epsilon-inset bounding-box block range and invokes `entityInside` then `onInsideBlock` without a path or inside-collision-shape test. In 1.21.2, `LivingEntity` calls `applyEffectsFromBlocks()` after `travel()` under the stated client/server-control gate; `Entity.applyEffectsFromBlocks` records the from/to movement and delegates to a new checker. That checker traverses blocks for each movement segment, deduplicates positions, applies inside-collision-shape filtering, then calls the two callbacks in the reported order. The Entity and LivingEntity sources are byte-identical in 1.21.2 and 1.21.3. The operation and boundary are accurate.

## Identity-supplement assessment and limits

The supplement corrects the report's former 1.21.1/1.21.3 provenance limitation. Both endpoint ledgers are bound by their ready markers and their relevant source entries match current file bytes. The 1.21.2 full manifest binds the three relevant source files to the exact-version Mojmap input set. I find no remaining source-identity blocker for these two bounded operations.

This verdict applies only to the two immutable finding drafts and evidence above. It is a findings-only independent source audit: it does not certify full-pair inventory/coverage, close unrelated dependencies, freeze the 1.21.1–1.21.3 pair, assess product implementation, or establish runtime parity.
