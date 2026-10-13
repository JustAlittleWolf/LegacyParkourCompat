# Independent review: F-008 edge-gate boundary memo

- Reviewer: independent Codex subagent `/root/f008_boundary_review`.
- Review date: 2026-10-09.
- Decision: **ACCEPT** for the exact memo snapshot below.
- Memo commit: `1bfc993c0602c00b22622d651c93fe640d4a4ac1`.
- Memo path: `workflows/source-boundary-reviews/2026-10-09-F008-border-edge-gate-boundary.md`.
- Memo Git blob: `864c15ea896e688c339d31b054963a6d6ea76b9a`.
- Memo raw SHA-256: `25cc8c489b5ddea46b07abae01db1bc5d444d28d38f7f075ad0631cc0f840040`.

The reviewer independently checked the exact source boundary and consumer paths, all four release readiness markers and source/artifact manifests, the border producer path including the clientbound initialization and center/lerp/immediate-size handlers, the accepted finding/review bindings, and the later `D-BORDER-MOVE-PATH` closure. The evidence supports the 1.18 first-changed release: 1.17.1 admits the border using the current box, while 1.18, 1.18.1 and 1.18.2 use the queried AABB under the nearby-border guard. The added client packet handler path closes the only requested source-evidence revision.

This review accepts only the source boundary memo and its bounded F-008 source claim. It does not review or accept Java implementation, source-pair runtime parity, or final player coordinates. The reviewer did not inspect the memo's resolver/implementation disposition section, mod implementation Java, or wiki/audit outputs. Implementation and runtime status remain separate.
