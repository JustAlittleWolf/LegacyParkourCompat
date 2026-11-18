# Swim-pitch vertical adjustment: 1.14.4–1.17.1 exact source evidence

This archive preserves eleven exact, independently accepted adjacent-version comparisons for the local player swim-pitch vertical adjustment across 1.14.4–1.17.1. Coverage is specific to this operation and these comparisons; it is not full movement-pair coverage.

The bounded operation is the swimming branch's use of the player's view vector to adjust vertical velocity, including the exact source gates, arithmetic, and call order. These records do not establish complete movement parity, runtime trajectories, or behavior beyond their compared endpoints. Adjacent lava-latch behavior is a separate operation and is excluded.

| Pair | Source report SHA-256 | Independent verdict SHA-256 | Supplemental identity SHA-256 |
| --- | --- | --- | --- |
| 1.14.4→1.15 | `fd068ee6d8f8dff82e882783bda32a0e3ce473f5733ffe1a5ef38a80964f67b0` | `2b2dd73c2f09a13e8643fb9d5464817cc2774300a005a2d0b1ac827da9e17673` | — |
| 1.15→1.15.1 | `480353ddbffb8b54c26049a6ba7fa05d0d9cee3be227a47c113ab1aa2ad91130` | `9b6ec7506d16d9c4be8094b96f6ad3e70b22cb9cd08ad77aebe5c5bb8f6d950f` | — |
| 1.15.1→1.15.2 | `649d0e171712abfd00f6fe5719e33938e2a464da531df7ecf2566d0cbd31bac8` | `f29efc544adc3bf4f73e29cda54f4553a0293fca339ba3bb08e5161d1ad76caf` | — |
| 1.15.2→1.16 | `537c3d21a3ce495b45560453420e7ca44fbbec4734e7e5ac6edd285dd7fb6f57` | `6eede967be468b8a3678e2cad82642007de09717f61c19893f1b9053f8409a2c` | — |
| 1.16→1.16.1 | `f6df5951c2512498886777eafad63f603f9f25ed77aace83e5251e1a1cd8ce91` | `05df3b56bcda55e725e29d05394215b459b4f4603fa5ccc122aee45109e09a51` | — |
| 1.16.1→1.16.2 | `f39bb4d7ac0ae20f24b667e71dc3b4331e8669aa9c198e3b90e5aaed9f7a2a45` | `bfd8327eff1cbc3c28603764e6cb0b13ba9ee1b5c9c76a1e9eab49375a062b49` | — |
| 1.16.2→1.16.3 | `a575b34af31afc22553f711ab45daa251498a66d4e892bf3b39a8255faf700fb` | `5aa90c4f4f19fefc0c01c405f9c443235d8f89d7f361d0e105b9e1a118cc0b1f` | Clean replacement report and strict blind verdict; passive inherited helper internals kept separate |
| 1.16.3→1.16.4 | `c263f870afe10fc2a394351a747fdc2cad52d153e94f7866aef87489244566e1` | `cfe1687088e64250f79e0e59b98ddf43c9720f002a4be07710d61047179e4803` | `identity.md`: `f1c673d64bf2b2b2a967c5181ccac369399b8bd2f69104049df165462d3cdf4f` corrects the digest transcription in the unchanged verdict |
| 1.16.4→1.16.5 | `2dedc1268cc7d1679869c51fa2a9bd102e53d9ec8e579f5592c19bda5028647e` | `91d8b8ae81aca5be720ff8d232a4abf7b623fc2c9840031119a8476053676274` | — |
| 1.16.5→1.17 | `f2524fcbc97a6f4d6cb7ba27ad2f83ce0d9f74b4a85cdd2bdd1d6e5c50de8343` | `d78fd7d03bd27bda66d950f9a338bfe261ed874cce784f68999aad1e0aabcc0d` | — |
| 1.17→1.17.1 | `0d36637149f9f0249d7889467d3055795c338665aa6bcb20aeb48e2c9668d4f3` | `451b4c1cb3ae4e9cbeaf45400e6de1316683a0c58afd84c9ee3fe6d04e4fbb18` | Source `identity.md` `a0aca63ebb31f008a9ea51d2201e5b5d433e1999cf35c39a25f0dcdd0a1d62e7`; audit `acceptance.md` `329f6cf232e4d805d1a1110fdac049ce6540a2f42176e2f71e6f39c46064b95d` |

The 1.15.2→1.16 audit corrects the cited move location. The 1.16.5→1.17 audit clarifies that `10430.378F` is a use-site literal, not a declaration; the numeric source result is unchanged. The 1.17→1.17.1 identity and acceptance sidecars preserve exact metadata binding. The 1.16.2→1.16.3 row uses only the clean replacement pair; earlier mis-scoped material is excluded.

This archive is based on `aae9c86464638cb5ed7faa535c92569b89484906`. It makes no product, backlog, or runtime claim.