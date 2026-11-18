# Exact author claim review: 26.1.1 → 26.1.2 Nether portal slice

## Immutable inputs

- Author claim: `source/comparison.md`, SHA-256 `218299f14cc3159287e27a60f5abe7aa37ee4b33c24344a428c63fe615b59ac6`.
- Blind freeze: `blind-audit/frozen.md`, SHA-256 `9fe94b92850a61fd3e2bd2c19ce1564e9ed3f1f68413a92926cae9dbaa4956ab`.

Both supplied digests were recomputed from the exact input bytes before review.

## Verdict

**ACCEPTED for the bounded same-input equivalence claim** covering ordinary unmounted player contact/admission, portal processor timing and cooldown/delay, ordered server-side transfer eligibility, and the authoritative position correction path in these two releases. The author's central no-difference conclusion agrees with the independent freeze. This is not full-pair coverage or a runtime-parity verdict.

The contact predicate, local state progression, player delay, and transition gates described by the author align with the freeze. The correction path also agrees through `ServerPlayer.teleport` and `ServerGamePacketListenerImpl.teleport`; the source-defined coordinate mutation occurs in the nested `Entity.teleportSetPosition` call to `Entity.setPosRaw`, whose first changed-coordinate write assigns `position`. The author's phrase “first position/rotation correction writer” is acceptable as shorthand for the listener entry point, but should name `setPosRaw` as the first coordinate assignment when claiming the first position writer precisely.

The author does not assert packet arrival or acknowledgment. Its clarified use of “arrival” refers to the accepted server-side transfer/correction and does not conflict with the freeze's packet-delivery exclusion. Its 26.1.2 provenance caveat also avoids claiming independently established official origin; provenance does not change this behavioral verdict.

## Residual limits

The author additionally claims a source-wide absence of other call sites and describes transition construction details (zero delta movement and relative flags) beyond the slices recorded in the freeze. Those supplemental claims are not independently accepted by this review. They are not needed for the bounded equivalence verdict above. Portal geometry/search, fluid contact, riding, End gateways, packet delivery/arrival, full-pair coverage, and runtime outcomes remain unreviewed.