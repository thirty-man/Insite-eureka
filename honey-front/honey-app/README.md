# React + TypeScript + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react/README.md) uses [Babel](https://babeljs.io/) for Fast Refresh
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react-swc) uses [SWC](https://swc.rs/) for Fast Refresh

## Expanding the ESLint configuration

If you are developing a production application, we recommend updating the configuration to enable type aware lint rules:

- Configure the top-level `parserOptions` property like this:

```js
   parserOptions: {
    ecmaVersion: 'latest',
    sourceType: 'module',
    project: ['./tsconfig.json', './tsconfig.node.json'],
    tsconfigRootDir: __dirname,
   },
```

- Replace `plugin:@typescript-eslint/recommended` to `plugin:@typescript-eslint/recommended-type-checked` or `plugin:@typescript-eslint/strict-type-checked`
- Optionally add `plugin:@typescript-eslint/stylistic-type-checked`
- Install [eslint-plugin-react](https://github.com/jsx-eslint/eslint-plugin-react) and add `plugin:react/recommended` & `plugin:react/jsx-runtime` to the `extends` list

```
honey-app
├─ .eslintrc.json
├─ .gitignore
├─ .prettierrc
├─ index.html
├─ package.json
├─ postcss.config.js
├─ public
│  └─ youtube.ico
├─ README.md
├─ src
│  ├─ api
│  │  └─ .gitkeep
│  ├─ App.tsx
│  ├─ assets
│  │  ├─ fonts
│  │  │  └─ .gitkeep
│  │  ├─ icons
│  │  │  └─ .gitkeep
│  │  └─ images
│  │     └─ .gitkeep
│  ├─ components
│  │  ├─ common
│  │  │  ├─ button
│  │  │  │  └─ TextButton.tsx
│  │  │  ├─ calendar
│  │  │  ├─ dropdown
│  │  │  │  └─ Dropdown.tsx
│  │  │  ├─ input
│  │  │  │  ├─ TextArea.tsx
│  │  │  │  └─ TextInput.tsx
│  │  │  ├─ modal
│  │  │  │  └─ Modal.tsx
│  │  │  └─ overlay
│  │  │     └─ Overlay.tsx
│  │  └─ pot
│  │     └─ Pot.tsx
│  ├─ config
│  │  └─ .gitkeep
│  ├─ index.css
│  ├─ main.tsx
│  ├─ pages
│  │  ├─ CreateRoom
│  │  │  └─ CreateRoom.tsx
│  │  ├─ HomePage
│  │  │  └─ HomePage.tsx
│  │  ├─ Login
│  │  │  └─ Login.tsx
│  │  ├─ RoomList
│  │  │  └─ RoomList.tsx
│  │  └─ Send
│  │     └─ Send.tsx
│  ├─ recoil
│  │  └─ Recoil.tsx
│  ├─ router.tsx
│  ├─ styles
│  │  └─ global.css
│  ├─ types
│  │  ├─ apiTypes.tsx
│  │  └─ routerTypes.tsx
│  └─ vite-env.d.ts
├─ tailwind.config.js
├─ tsconfig.json
├─ tsconfig.node.json
├─ vite.config.ts
└─ yarn.lock

```