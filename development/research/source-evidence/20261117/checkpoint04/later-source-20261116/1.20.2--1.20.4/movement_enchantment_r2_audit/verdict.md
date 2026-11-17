# Independent blind source audit verdict: movement enchantment identity r2

Candidate: `build/later-source-20261116/1.20.2--1.20.4/movement_enchantment_identity_r2/report-r2.md`
Candidate SHA-256: `4F15DDC1054B0DABB8FF6C29353B387E4863221CA2AA6BA2AC165DD88E605EAD`

## Verdict

**Accept the bounded source-evidence and no-difference claims as stated.** This verdict covers only the reported Depth Strider and Soul Speed slices for exact 1.20.2 and 1.20.4 Mojmap decompilations. It does not accept a pair-wide freeze, implementation boundary, implementation, or runtime parity.

## Independent checks

- The candidate bytes hash to the SHA-256 above. Both root `mojmap.ready.json` records report `ready` and the matching version and `mojmap` namespace. Both `mojmap.provenance.json` records agree on version, namespace and mapping option, and list the same exact-run batch containing 1.20.2 and 1.20.4.
- Ready/provenance/source-manifest hashes match the report: ready `5FF3322F…92483F` and `6225B979…044D1`; provenance `1DAD9F72…D4F05` and `10914D9B…C1385`; source manifests `E439FF7E…62887` and `FD3C8668…602A1`. Reparsed all source manifest entries and rehashed every Java source: 4,904/4,904 (23,604,756 bytes) for 1.20.2 and 5,048/5,048 (24,323,941 bytes) for 1.20.4 matched, with no missing or malformed entries.
- The cited `LivingEntity.java`, `Entity.java`, `Player.java`, and `EnchantmentHelper.java` SHA-256 values in the report match the current files and source manifests. I retraced the reported slices. `LivingEntity.travel` has the same local-water gate, Depth Strider cap, airborne halving, interpolation constants/order, and Dolphin’s Grace override at both endpoints. The `getDepthStrider` forwarding method is identical. Soul Speed’s block-tag test, positive-level check, `1.0F` factor, player flying/fall-flying overrides, and `Entity.move` X/Z multiplication match. The `0.03F * (1.0F + level * 0.35F)` transient addition formula and the listed add/remove call gates also match.
- Artifact manifests are root sidecars named `decompiled_minecraft/<version>/artifacts.sha256` (the ready records name `artifacts.sha256`). Their hashes and 69-entry counts match the ready declarations. The provenance cache directory is absent in this checkout, so artifact payload bytes could not be rehashed. The candidate states this limitation accurately and does not claim artifact payload verification.

## Preserved open gates

Effective enchantment/inventory lookup, footwear eligibility, Soul Speed tag membership, attribute synchronization/effective client state, full player-tick and other consumer coverage, complete pair coverage, independent full-pair audit/freeze, implementation, and runtime parity remain open as the candidate says. No claim here closes those dependencies.
