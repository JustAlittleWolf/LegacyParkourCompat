# Gliding look trig audit verdict

**COMPLETED** — independently audited the immutable comparison at `glide-look-exact-range-source/comparison.md`.

## Verdict

The 1.13 direct-radian vector formula is source-confirmed by 1.13, 1.13.1, and 1.13.2. All three have the same `Entity`, `LivingEntity`, and `MathHelper` file bytes, and the formula also remains in the inspected 1.14 and 1.14.4 endpoints. The 1.12.2 formula is the distinct older `-yaw·rad−π` / `-pitch·rad` expression. Therefore the narrow proven source interval is: old form at 1.12.2; direct-radian form by 1.13 and retained through 1.14.4. This establishes 1.13 as the first inspected release with the new form. The exact release of introduction is bounded to after 1.12.2 and no later than 1.13; no intermediate patch boundary is claimed.

The float trig lookup is a separate operation. 1.13 and 1.14.4 call float `sin`/`cos`, using the float scale `10430.378F` and float table. The inspected 1.21.11 and 26.2 helpers instead accept `double` and use `10430.378350470453` for table construction and lookup. The direct-radian vector expression itself still matches in 26.2, but its trig lookup path diverges from the 1.13–1.14.4 path for some inputs. The comparison's yaw `3.2547F` witness demonstrates different truncated indices. The narrowest supported interval for this later lookup change is **after 1.14.4 and no later than 1.21.11**; the first stable release carrying it is unknown from these endpoints.

The cardinal-yaw witness also checks out from source operation order: at pitch/yaw zero, the 1.12.2 expression indexes the sine table at 32768 and returns a small nonzero component, while the 1.13 expression indexes at zero and returns positive zero. This distinguishes the expression change independently of the earlier head-yaw/body-yaw provider transition.

**Annotation:** Sources support `@MovementChange(emulates = 1.13)` for the direct-radian formula transition as first observed. They do not support assigning an exact annotation release to the later float-to-double lookup change: inspecting 1.14.4, 1.21.11, and 26.2 leaves a wide unresolved cutover. More exact intervening stable sources are required before claiming that later boundary.

## Evidence integrity

The draft SHA-256 matches the required identity: `886c3d43e7eb92d9aec14d804669c122fef38f3520d7c073d5e899109921eb55`.

The hidden mapping-local `.decompile-provenance.json` is absent in every inspected mapping directory. However, each exact version/mapping has an outer ready marker and provenance sidecar. The ready markers report the expected version, namespace and `ready` status; their recorded source-manifest hashes match the local outer source-manifest files. The inspected source-file hashes listed in the comparison match the local canonical `decompiled_minecraft/<version>/<mapping>/` files and their outer source manifests. The outer provenance sidecars identify the expected exact versions and mapping namespaces. Thus the source bytes and their local published-manifest association are verified; the absent nested manifest is not silently treated as present.

No exact patch boundary is claimed for the later lookup change. No product implementation, wiki, Git history, tests, generation or runtime behavior was used.

## Terminal identities

- Comparison SHA-256: `886c3d43e7eb92d9aec14d804669c122fef38f3520d7c073d5e899109921eb55`
- Verdict SHA-256: reported in the terminal handoff (self-hash is kept outside this file).