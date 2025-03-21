import {defineStore} from "pinia";
export const useAuthStore = defineStore("authStore", () => {
 const token = ref(null)
 const user = ref(null)
 const router = useRouter();

 function logout() {
 token.value = null 
 user.value = null

 }

 function setUser(userData){
  user.value = userData
 }


 return { token, user, logout, setUser}
}) 
