import {useAuthStore} from "~/store/auth-store";
export default defineNuxtRouteMiddleware((to, from) => {
    const authStore = useAuthStore();  // Pegando o auth store

    // Verifique se o usuário está autenticado
    if (!authStore.token) {
        // Redireciona para a página de login se não estiver autenticado
        return navigateTo('/');  // Ou qualquer outra página que você preferir
    }

    // Verifique se o usuário é um "employee" (exemplo de permissão adicional)
    if (to.path.startsWith('/employee') && (authStore.user.dtype !== 'Employee' && authStore.user.dtype !== "Manager" )) {
        // Se for uma rota de funcionário e o usuário não for um funcionário, redireciona
        return navigateTo('/');
    }
});