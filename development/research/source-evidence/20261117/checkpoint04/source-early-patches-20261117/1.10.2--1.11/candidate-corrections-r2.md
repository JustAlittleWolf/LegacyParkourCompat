# 1.10.2 → 1.11 candidate corrections r2

This immutable correction note responds to the independent audit verdict `build/source-early-patches-20261117/1.10.2--1.11/audit/full-pair-verdict.md`, SHA-256 `4a7404e4dfe1da4012378ee00428b55b9f129036540dfcfe37d39115bb20246c`.

Bound original inputs remain unchanged:

- Coverage report: `coverage.md`, SHA-256 `B6789F01DE090321F9F05E79A99B35E9EE6739A141A26DED0B510D643FB24069`.
- Sneak-edge candidate r1: `findings/sneak-edge-piston-mover-type.md`, SHA-256 `2A6A07709AA63DDFB62FEBB5B31170F987BF2FE035AC45315267983A477C1EAF`.
- Moving-piston candidate r1: `findings/moving-piston-client-response.md`, SHA-256 `6634A107D75460D8BAB21D9D6A14CEEDE2D5E8B8F6E5C88D2BE42941D3BB8694`.

Corrected immutable candidate files:

- `findings/sneak-edge-piston-mover-type-r2.md`: states the `noClip == false` and non-spectator selection gates. It clarifies that 1.11 `NOCLIP_DIR` suppresses the moving piston entity's own collision shape during a push while progress is below 1.0, but other world collision boxes can still clip movement.
- `findings/moving-piston-client-response-r2.md`: adds the same non-spectator and `noClip == false` limits and the exact moving-shape suppression qualification. Its source-level movement observations remain conditional; no net trajectory is claimed.

No source files, original reports, or audit verdicts were changed. The audit accepts the coverage report only as partial inventory and does not freeze this pair. Independent review of these corrected candidate claims remains required.
