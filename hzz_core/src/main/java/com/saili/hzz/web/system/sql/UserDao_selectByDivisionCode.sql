select
    user.id,
    buser.username
from t_s_user user
left join t_s_base_user buser on buser.id = user.id
left join t_sl_division division on division.code = buser.division_divisionCode
where division.code = :code
;