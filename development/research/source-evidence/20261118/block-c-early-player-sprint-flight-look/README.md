# Early sprint, Elytra-input, and glide-look evidence (2026-11-18)

This archive binds five exact sprint/flying-input comparison pairs and three exact glide-look/dismount comparison pairs. Each report and independent verdict is preserved byte-for-byte. Their bounded claims stay with the exact version pair and evidence named in each audit; they do not establish full-pair coverage, implementation, runtime parity, or a general result for adjacent unreviewed releases.

| Pair | Scope | Report SHA-256 | Independent verdict SHA-256 |
|---|---|---|---|
| 1.8.9→1.9 | Sprint-use timer and flying-input behavior. Audit corrects report wording: 1.8.9 reads/decrements the old timer; 1.9 does not read it. The flight reset/rescale result is separately accepted. | `bf5401df3f098be3e2072e7f140d4587503c7782cdc7a341c3a14ebf81dc617b` | `56fae6a6cad7457ccc3dd5dcec051766ea0008584c857640a73c5e6b6136d195` |
| 1.9→1.9.1 | Exact sprint/flying-input source review; see bound verdict for its specific operation and qualifications. | `b3f454307d47f01d176891d11e60b006cbe8b9c6a0412549fbeb7c8ea5f75f0a` | `9c509aff69a7925ef87586ebac7c3ee91307f7f4ac21ea5a347fbd679738a3cb` |
| 1.9.1→1.9.2 | Exact sprint/flying-input source review; report SHA is recovered from and bound by the frozen verdict. | `44facdcbd45e29c5195ce63aad0ca98528a1d86fde44b25ccf648ba82a71c116` | `8e66349786b24150bfe2ce27ceebcfe9286ca9fb245b003258b27d40941a7f46` |
| 1.9.2→1.9.3 | Exact sprint/flying-input source review. | `664c031d60bc371f9bc857f3a73fa2f639af8171c112ea0cd1a91e9440438085` | `47b33edabb6d36cf5b84d64a024e5beb1f0815539eb3f41b56770b9a2aae514f` |
| 1.9.3→1.9.4 | Exact sprint/flying-input source review; report notes raw-artifact availability separately from checked source-manifest integrity. | `77d8d021eb9b680f234ebb6badfbb37129bb64bed3d781004403c8b292db24d0` | `1a7c5c509ca1e296781f34541cbe3c48e79b1f22474fb122d685a8b528ce31fb` |
| 1.11.2→1.12 | Current movement-yaw and ordinary direct dismount-selection review. The dismount selection is server-gated; collision geometry is outside scope. | `9ac7e99640f22c7c7ad9ff2b0ee3457b5114e8812dadb808e468d6329137a694` | `3588517498e0a3d98f35a4f771ca906e9b7337112a5d92452f246f479c0310e5` |
| 1.12→1.12.1 | Current movement-yaw and dismount-selection review; exact metadata and sources are bound in the report and verdict. | `1db94cf0b7a1c7e85abffe2fe3aec9d8536f2a25215a81fbb0788a750bd7931e` | `46ec5655807b3202c9aeedea51d62d05bae28de86077eb99e3741a11553f7cee` |
| 1.12.1→1.12.2 | Current movement-yaw result. Audit notes the selected dismount position is not directly sent as a local-self position writer; server gating and excluded geometry remain separate. | `1aa7b1bddaff87bffc0f53331f0eb1ce22f0826b08e7f2ea8b44d11daea11c7c` | `19601d4e629c40ff57404860254a12980a45086d851a64940886852e4b94503b` |

The five sprint/flying-input reports are under `1.8.9-to-1.9/` through `1.9.3-to-1.9.4/`; the three glide-look/dismount reports are under `1.11.2-to-1.12/` through `1.12.1-to-1.12.2/`. Every verdict binds the exact archived report hash shown above. The .9.1→.9.2 verdict's full SHA-256 was rehashed from the frozen audit bytes, including the handoff's malformed whitespace.

Ability-flight `fallDistance` reconciliation remains a separate open question pending its exact Feather naming/order audit. This archive makes no implementation claim on that question. No product, backlog, or runtime state is represented here.
Frozen evidence reports/verdicts are byte-preserved, including any original trailing spaces, blank EOF lines, or embedded NUL bytes. Accordingly, the scoped whitespace check excludes trailing-space and blank-at-EOF diagnostics for these immutable input copies; SHA-256 checks verify their exact bytes.
