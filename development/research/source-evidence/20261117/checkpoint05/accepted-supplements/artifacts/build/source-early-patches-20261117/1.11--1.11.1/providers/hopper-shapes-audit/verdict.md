# Hopper collision provider audit — ACCEPT

Audited report: `build/source-early-patches-20261117/1.11--1.11.1/providers/hopper-shapes.md`  
Report SHA-256: `c0d079e5b918f7c23f09d3ae5a4a8150258644056f5d6c346f150f4c71f823a2`

## Verdict

**ACCEPT** for the bounded Hopper provider comparison and local-player collision-query binding described in the report. Both source identities and the stated claims were independently checked against the exact 1.11 and 1.11.1 Ornithe Feather trees and source manifests.

The `FACING` property filters out `UP`, leaving five directions; `ENABLED` is boolean, so the legal matrix has ten states. In both releases, `getShape` returns the full-block shape and `addCollisions` appends the same five local boxes in the same order without consulting either property. Each box is passed through the same `Block.addCollision` behavior: translate by block position and append only if the query box intersects it. In 1.11.1 the added `forceShape` argument is unused by Hopper, and the ordinary world collision loop passes `false`. The state forwarder and local-player `Entity.move` collision-query path match the report.

## Identity checks

The report hash matches. The listed Hopper source hashes match the files and their `.sources.sha256` rows:

- 1.11 `HopperBlock.java`: `9006e14d8e9edd8b966eeec52848bd8efe8dbe97c6b109ccb471d72efbb3ab52`
- 1.11.1 `HopperBlock.java`: `c372402b14b648b0c2ab9ef5629accc9ae0e4cb5a9a78b7a0d4b7e2fa524a828`

The provenance, ready-marker, source-manifest and artifact-manifest hashes in the report match the exact sidecars. The supporting `Block.java`, `StateDefinition.java`, `World.java`, `Entity.java`, and `LocalClientPlayerEntity.java` files were also matched to both source manifests.

## Limits

This accepts only the Hopper provider result and its ordinary local-player collision-query binding. It does not assess Hopper inventory/item transfer or state production, other providers, trajectory consequences, remaining pair inventory, or pair freeze. The exact 1.11→1.11.1 pair remains partial pending its separate coverage and dependency audit.
