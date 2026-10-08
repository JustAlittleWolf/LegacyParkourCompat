# MCPK corrected-defect follow-up — 2026-10-08

Reviewer: independent follow-up in a new isolated worktree. Review started from correction commit `7dffdda43c77330e8eedff1d09344648c4805ef2` on `fix/mcpk-clean-rereview-dispositions`. Scope is limited to the corrected edge-backoff predicate and slipperiness snapshot provenance. The earlier Big Dripleaf and swimming ACCEPT dispositions in review commit `6e045f3d50f9248ab329c5bd698caa935e8a9be3` remain unchanged.

## Dispositions

| Finding | Disposition | Follow-up result |
|---|---|---|
| 1.16.1→1.16.2 sneak edge-backoff, r3 | **ACCEPT** | The corrected summary has the polarity right: the airborne branch requires `fallDistance < maxUpStep` and `!noCollision(player, candidateBox)`. Since `noCollision` is true only when every returned collision shape is empty, its negation means the candidate box collides with at least one non-empty collision shape. `fallDistance < maxUpStep` is strict. The old `onGround` gate is removed; the new gate also excludes flying, keeps `SELF`/`PLAYER` movement and the sneak-key predicate, and accepts grounded players through the first `isAboveGround()` branch. The candidate box is moved by the exact vertical expression `fallDistance - maxUpStep`. `Entity.move` invokes the virtual hook before collision resolution at both endpoints. The existing X-only, Z-only, then combined probes and their 0.05 adjustment/clamp logic are unchanged. This remains bounded to the source-defined gate, not arbitrary airborne movement. |
| 1.14.4→1.15.2 slipperiness sample examples, corrected identity | **ACCEPT** | The exact immutable r2 snapshot still has the previously reviewed source-accurate bytes and content hash. The updated identity register now gives its actual Git blob ID `96ca89de5ef2b4e84e2fd591296ab620651ee7c3`; this matches `git rev-parse` at the registered commit:path. No source content or prior source disposition changed. |

## Exact identities

| Artifact | Exact commit:path | Git blob | Content SHA-256 |
|---|---|---|---|
| Edge-backoff r3 | `10d4937470804e6ffd75b35930a9c3d62044b9be:workflows/wiki-audit-2026-10-07/mcpk-1.16.2-step-down-snapshot-r3.md` | `2c7eeff1b8c1e9ab2913035a647a5f455befa20b` | `182edbae3bed0e878f890c2a98ee402e51d0b8de91e3a954ce8030e6a77e9857` |
| Superseded edge-backoff r2 | `78683ba65928004ce8b7b6b9371359164a68d43b:workflows/wiki-audit-2026-10-07/mcpk-1.16.2-step-down-snapshot-r2.md` | `3ea28bac8e53ec5d6be2ee99d98001841102eb02` | `d2dae102961157b575d871918c5112243b2a89eb2b412885131facc77e28ad79` |
| Slipperiness r2 | `78683ba65928004ce8b7b6b9371359164a68d43b:workflows/wiki-audit-2026-10-07/mcpk-1.15-slipperiness-examples-snapshot-r2.md` | `96ca89de5ef2b4e84e2fd591296ab620651ee7c3` | `1edaad992b3993fad310d568490421e48f6d4d32d836647f137f71261c67988c` |
| Corrected identity register | `7dffdda43c77330e8eedff1d09344648c4805ef2:workflows/wiki-audit-2026-10-07/mcpk-clean-rereview-candidates-2026-10-08.md` | `082151e92aac233284794a849c318bfba48e5d6e` | `3c65c7fab9fe0359fd8e0ca4ecb22781e4d208ed3f63bf9ea4b9662b0e8aa157` |
| Prior clean review | `6e045f3d50f9248ab329c5bd698caa935e8a9be3:workflows/wiki-audit-2026-10-07/mcpk-clean-rereview-dispositions-2026-10-08.md` | Referenced for the prior two ACCEPT dispositions; not changed by this follow-up. | — |

The r3 snapshot supersedes r2 and retains the same bounded source scope. The register’s corrected slipperiness blob ID and the r2 content SHA-256 both match the immutable snapshot. The r3 Git blob and content SHA-256 match the register.

## Edge-backoff source identities

The cited files were re-hashed from the canonical ready Mojmap trees; each source row and both source/artifact manifest pairs match the r3 values.

| Endpoint | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|
| `1.16.1/mojmap` | `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754` | `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98` |
| `1.16.2/mojmap` | `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7` | `7b8551bdc108a1584039ac22e35b6b75c3584ed85aba24561c7d340f5cbeb108` |

Source rows: 1.16.1 `Player.java` `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e`; 1.16.1 `Entity.java` `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576`; 1.16.2 `Player.java` `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`; 1.16.2 `Entity.java` `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`; and 1.16.2 `CollisionGetter.java` `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac`.

## Limits and process

The preserved MCPK direct-page fetch returned HTTP 403; this pass does not verify live page wording or revision metadata. The finding uses Mojmap endpoints and does not establish Feather-mapped source equivalence to an original mapped JAR. No broad pair coverage, implementation coverage, or runtime parity is established.

I read only the required global guidance, the exact corrected identity register, the exact r3 and slipperiness snapshots, the prior clean review, and the cited vanilla source dependencies. No unrelated workflow contents, source-pair findings, Minecraft Wiki material, or mod implementation were read. No tests, builds, runtime tools, Docker, pushes, or messages to other chats were used.
