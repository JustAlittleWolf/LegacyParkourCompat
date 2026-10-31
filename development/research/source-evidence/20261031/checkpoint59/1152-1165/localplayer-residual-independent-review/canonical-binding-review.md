# Canonical draft binding — LocalPlayer residuals, 1.15.2 → 1.16 Mojmap

## Immutable inputs

| Artifact | SHA-256 |
|---|---|
| Canonical `open-shulker-stops-local-escape-impulse.md` | `50147c54a3b34d644f52799feb2b32dc0726a751f3d5f52334db492ea3b5dd27` |
| Canonical `fall-flying-request-climbable-tag-gate.md` | `16107b581c8a8cb9d1c4940026adb2e2ee9d42dd1bd0799c29cfbce98b924a08` |
| Boundary report `localplayer-residual-boundaries/report.md` | `6f02c51479aac2cbc77984826542159400eb0de821210904c6cb697612b08cd4` |
| Accepted source review `localplayer-residual-independent-review/review.md` | `adba017de4d4be46231c539b248171d553c91b34f56dec4e1bf206d25f107d82` |
| Accepted input inventory `remaining-localplayer-member-inventory/report.md` | `5278c1ba1283bf289c46751a192c975ffcbfc6e2129eeb044ac6c9a7f1361545` |

The three requested hashes were recomputed from the current files and match. The boundary report pins the seven audited exact releases (1.15.2, 1.16, 1.16.1, 1.16.2, 1.16.3, 1.16.4, 1.16.5), and records source-manifest/provenance identity plus every cited file's matching source-manifest entry or same-release embedded source map. Direct checks of the 1.16.0 member bodies agree with the draft claims.

## Per-finding verdicts

### `open-shulker-stops-local-escape-impulse.md` — ACCEPT

The draft describes the right operation: LocalPlayer's four `aiStep` `checkInBlock` probes and their direct velocity response. Its 1.15.2 evidence is coherent: `checkInBlock`/`blocked` and the caller are cited; the Shulker override returns true unconditionally. For the supplied open EAST-facing Shulker witness, the strict directional order retains EAST on the last equal-distance EAST/SOUTH comparison and writes `+0.1` X.

The 1.16 evidence is also the exact first changed provider path: LocalPlayer still calls `checkInBlock` (1.16 `LocalPlayer.java:414–458, 678–685`); `blocked` uses inherited `Player.freeAt` (`Player.java:1421–1423`); the Shulker suffocation override is gone, and `Blocks.shulkerBox` installs the conditional state predicate (`Blocks.java:3172–3184`). A matching opened BE has `isClosed() == false` (`ShulkerBoxBlockEntity.java:290–292`), suppressing the old response in 1.16.0 already. The boundary report correctly distinguishes the later 1.16.2 caller/helper refactor from this 1.16 provider-response boundary. The title and movement meaning stay within the direct local velocity response and do not claim a trajectory.

The pair label `1.15.2 → 1.16` is supported as the compared endpoint boundary and earliest audited new behavior. It is not interpreted as a finer pre-release cutover claim.

### `fall-flying-request-climbable-tag-gate.md` — ACCEPT

The draft isolates the LocalPlayer `aiStep` request gate, with the old `!onLadder()` at 1.15.2 `LocalPlayer.java:715–720` and the first audited new `!onClimbable()` at 1.16 `:750`. The boundary report confirms that gate persists through 1.16.5. The old helper's explicit LADDER/VINE/SCAFFOLDING plus aligned open-trapdoor case and the new helper's `BlockTags.CLIMBABLE` plus same trapdoor case are accurately contrasted. Effective membership is data-driven and is not established by the Java sources; the draft explicitly conditions the difference on a mismatch with the old explicit set and disclaims a default tag-only witness. No claim about arbitrary or default dynamic tag states is made.

The request's jump snapshot, flight-toggle, ability, passenger, Elytra identity/enabled, and packet gates match the reviewed source route. `Player.tryToStartFallFlying` does not add a climbability test. The separate 1.16 Levitation denial in that method is kept separate from this claim. The draft describes possible movement-branch selection only if request eligibility changes. The endpoint wording is consistent with first checked appearance in 1.16 and does not assert a finer pre-release introduction time.

## Source member identities checked

| Release | Relevant member(s) | Complete source-file SHA-256 |
|---|---|---|
| 1.15.2 | `LocalPlayer.java`: `checkInBlock` 385–429, `blocked` 431–443, `aiStep` calls 650–655 and fall-flying request 715–720; `ShulkerBoxBlock.java`: `isSuffocating` 62–64; `LivingEntity.java`: `onLadder` 1226–1251 | LocalPlayer `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`; Shulker `de5e19cffb75499b509bc361fcfb3dbe0b0fa7ab490ac42adedf89466721b02d`; LivingEntity `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` |
| 1.16 | `LocalPlayer.java`: `checkInBlock` 414–458, `blocked` 460–472, `aiStep` calls 678–685, fall-flying request 750–754; `Player.java`: `freeAt` 1421–1423; `Blocks.java`: Shulker registration 3172–3184; `ShulkerBoxBlockEntity.java`: `isClosed` 290–292; `LivingEntity.java`: `onClimbable` 1361–1378; `BlockTags.java`: tag binding 79 | LocalPlayer `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b`; Player `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e`; Blocks `61f18f903f7d96731bbc3cac544a8614f55fddac872dc0b0fee5a53511c313a9`; Shulker BE `4aab41f71aacf9e1138d954c24ce43d0828755947c9ca5c642be01f4ffe589517`; LivingEntity `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26`; BlockTags `7f506e2386851a128c12a449683b6f4fe1d0007fe2e74524116df73c8db30a7d` |
| 1.16.5 | Persistence of both routes and predicate, as boundary check; this is not the finding's 1.16 onset evidence | Hashes and exact source mappings are preserved in the bound boundary/source review reports |

No product, wiki, backlog, source-generation, test, build, launch, or runtime activity was performed.
