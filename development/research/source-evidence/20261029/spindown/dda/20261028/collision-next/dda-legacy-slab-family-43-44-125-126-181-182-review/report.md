# Independent review: 1.8.9 slab-family aliases and providers

## Verdict

**ACCEPT, within the report's old-state decoding, 26.2 alias, and collision-provider scope.** The report does not claim full DDA traversal or runtime parity, and this review does not extend it.

Reviewed report SHA-256: `F5446AA013AF10B69169CFB52176B83AD74344E105B3185157185DCA54AEA7AA`.

## Integrity

I independently recomputed all 21 entries in `sources.sha256` against the canonical read-only 1.8.9 and 26.2 roots and source manifests. All entries matched. The ready-marker and source-manifest identities in the artifact also matched the canonical files.

## Claims checked

- 1.8.9 registry IDs 43, 44, 125, 126, 181, and 182 resolve to the stated double/single stone, wooden, and red-sandstone slab registrations. Their decoder paths mask the low three variant bits; double stone uses bit 3 for `seamless`, single slabs use bit 3 for bottom/top, and double wooden slabs ignore bit 3. The fallback details are correct: wooden variant indices 6 and 7 resolve to oak; the one-entry red-sandstone enum resolves every masked variant to red sandstone. No invalid metadata case remains after the masks for these providers.
- The block-state flattening rows map the legacy variants to the listed current slab keys and `type` values. In particular, legacy `wood_old` maps to `petrified_oak_slab`, not `oak_slab`; the old stone slab key is renamed to `smooth_stone_slab` by the 26.2 block rename fix; and legacy red sandstone variants all map to `red_sandstone_slab`. The seamless full-block outcomes for smooth stone, smooth sandstone, smooth quartz, and smooth red sandstone are correctly identified as full-cube geometry.
- The old slab provider writes shared mutable block bounds before collision insertion, with double slabs using a full cube and single slabs selecting bottom/top from live state. The current `SlabBlock` provider returns those same bottom/top/double bounds from `type` and ignores `waterlogged` for collision shape. The old shared-bound writes remain a separate observer/lifecycle issue, as the report says.
- Current slab collision is enabled by the default `hasCollision=true` gate, and the 26.2 mapped slab registrations do not disable it. The report scopes that statement to slab registrations and does not imply the separate smooth full-block outcomes are slab instances.

## Scope limits

The report correctly leaves DDA visitation, chunk availability, unrelated block providers, and any need to reproduce old shared-bound side effects open. This ACCEPT covers only the checked state aliases and provider geometry/gates.
