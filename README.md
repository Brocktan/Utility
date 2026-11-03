# Fake Utility — Fabric 1.21.4 (client-side)

This is a **source project** for a client-only visual disguise mod. Press **K** to open the Fake Lootdrop editor.

## Fake Lootdrop editor
- Add a mapping with namespaced registry IDs, e.g. `minecraft:stone` -> `minecraft:diamond_block`.
- Remove a mapping by entering its original ID and pressing **Remove Original ID**.
- Mappings are saved to `.minecraft/config/fakeutility-lootdrop.json`.
- Default mappings: purpur block -> netherite block; terracotta -> gilded blackstone; iron chestplate -> elytra; tropical fish -> enchanted golden apple; blue concrete -> spawner.

## Build
Requires Java 21 and internet access for Gradle dependencies.

Run `gradle build` from this folder (or generate a Gradle wrapper first).

The compiled jar will be in `build/libs/`.

## Important limitations
This project is client-only. It changes client rendering only; it does not change server blocks, inventories, drops, balances, or server-side commands. The current rendering hooks are an initial implementation and need compile/runtime verification against the exact Yarn/Fabric toolchain. Block state properties (e.g. facing/open state), entity equipment rendering, armor stands, custom item components, and all special render paths may need additional hooks for perfect coverage. No server economy or gameplay state is modified.
